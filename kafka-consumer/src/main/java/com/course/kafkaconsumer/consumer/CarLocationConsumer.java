package com.course.kafkaconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.CarLocation;


@Service
public class CarLocationConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(CarLocationConsumer.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@KafkaListener(topics = "t_location", groupId = "cg-all-location")
	public void listenAll(String message) throws JsonMappingException, JsonProcessingException {
		var carLocation = objectMapper.readValue(message, CarLocation.class);
		
		logger.info("listen All : {}",carLocation);
	}
	
	@KafkaListener(topics = "t_location", groupId = "cg-far-location", containerFactory = "farLocationContainerfactory")
	public void listenFar(String message) throws JsonMappingException, JsonProcessingException {
      var carLocation = objectMapper.readValue(message, CarLocation.class);
		
		logger.info("listen Far : {}",carLocation);
		
	}

}
