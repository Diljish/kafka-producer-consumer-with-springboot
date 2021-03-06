package com.course.kafkaconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class FixedRateConsumer {
	
	private Logger logger = LoggerFactory.getLogger(FixedRateConsumer.class);
	
	
	@KafkaListener(topics ="t_fixedrate_2")
	public void consumeMessage(String message) {
		logger.info("Consuming : {}"+message);
		
	}
	
	

}
