package com.course.kafkaconsumer.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaKeyConsumer {
	
	
	private Logger logger =LoggerFactory.getLogger(KafkaKeyConsumer.class);
	
	@KafkaListener(topics = "t_multipartitions" ,concurrency = "5")
	public void consume(ConsumerRecord<String, String> message) throws InterruptedException {
		logger.info("key : {} ,Partitions : {} , Message : {}",message.key(),message.partition(),message.value());
		Thread.sleep(1000);
	}

}
