package com.course.kafkaconsumer.config;
import java.io.IOException;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import com.course.kafkaconsumer.error.handler.GlobalErrorHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.CarLocation;

@Configuration
public class KafkaConfig {
	@Autowired
	private KafkaProperties kafkaProperties;

	@Bean
	public ConsumerFactory<Object, Object> consumerFactory() {
		var properties = kafkaProperties.buildConsumerProperties();

		properties.put(ConsumerConfig.METADATA_MAX_AGE_CONFIG, "120000");

		return new DefaultKafkaConsumerFactory<Object, Object>(properties);
	}
	
	@Bean(name = "farLocationContainerfactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object>farLocationContainerfactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
		
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		
		factory.setRecordFilterStrategy(new RecordFilterStrategy<Object, Object>() {

			ObjectMapper objectMapper = new ObjectMapper();
			@Override
			public boolean filter(ConsumerRecord<Object, Object> consumerRecord) {
				try {
					var carLocation = objectMapper.readValue(consumerRecord.value().toString(), CarLocation.class);
					return carLocation.getDistance() <=100;
				} catch (IOException e) {
					
					e.printStackTrace();
					return false;
				}
			
			}
			
		});
		return factory;
		
	}
	
	@Bean(name = "kafkaListnerContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object>kafkaListnerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		factory.setErrorHandler(new GlobalErrorHandler());
		return factory;
	}
	
	private RetryTemplate createRetryTemplate() {
		var retryTemplate = new RetryTemplate();
		var retryPolicy = new SimpleRetryPolicy(3);
		retryTemplate.setRetryPolicy(retryPolicy);
		var backOffPolicy = new FixedBackOffPolicy();
		backOffPolicy.setBackOffPeriod(10_000);
		retryTemplate.setBackOffPolicy(backOffPolicy);
		return retryTemplate;
		
	}
	
	@Bean(name = "imageRetryContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object, Object>imageRetryContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer){
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		factory.setErrorHandler(new GlobalErrorHandler());
		factory.setRetryTemplate(createRetryTemplate());
		return factory;
	}
	@Bean(name = "invoiceDltContainerFactory")
	public ConcurrentKafkaListenerContainerFactory<Object , Object>invoiceDltContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer, KafkaTemplate<Object, Object> kafkaTemplate){
		var factory = new ConcurrentKafkaListenerContainerFactory<Object, Object>();
		configurer.configure(factory, consumerFactory());
		
		
		
		var recover = new DeadLetterPublishingRecoverer(kafkaTemplate,
				(record, ex) ->new TopicPartition("t_invoice_dlt", record.partition()));
		factory.getContainerProperties().setAckOnError(false);
		
		var errorHandler = new SeekToCurrentErrorHandler(recover,5);
		factory.setErrorHandler(errorHandler);
		return factory;
	}
	

}
