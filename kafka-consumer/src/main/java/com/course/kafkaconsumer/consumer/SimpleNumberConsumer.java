package com.course.kafkaconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.SimpleNumber;

public class SimpleNumberConsumer {
	
	private Logger logger =LoggerFactory.getLogger(SimpleNumberConsumer.class);
	
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@KafkaListener(topics = "t_simple_number")
	public void consume(String message) throws JsonMappingException, JsonProcessingException {
		var simpleNumber = objectMapper.readValue(message, SimpleNumber.class);
		if(simpleNumber.getNumber() %2 != 0) {
			throw new IllegalArgumentException("Odd number");
			
			
		}
		
		logger.info("valid number: {}", simpleNumber);
		
		
	}
	

}
