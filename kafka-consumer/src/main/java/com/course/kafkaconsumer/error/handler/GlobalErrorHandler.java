package com.course.kafkaconsumer.error.handler;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;
import org.springframework.kafka.listener.ConsumerAwareListenerErrorHandler;

public class GlobalErrorHandler implements ConsumerAwareErrorHandler {

	
	private Logger log = LoggerFactory.getLogger(GlobalErrorHandler.class);
	@Override
	public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
		log.warn("Global error handler : {}", data.value().toString());
		
	}
	
	
	

}
