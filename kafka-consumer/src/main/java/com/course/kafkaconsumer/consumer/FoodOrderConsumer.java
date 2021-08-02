package com.course.kafkaconsumer.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.FoodOrder;

@Service
public class FoodOrderConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(FoodOrderConsumer.class);
	
	private ObjectMapper mapper =new ObjectMapper();
	
	private static final int MAX_AMOUNT_ORDER =7;
	
	@KafkaListener(topics = "t_food_order", errorHandler = "myFoodOrderErrorHandler")
	public void consume(String message) throws JsonMappingException, JsonProcessingException {
		
		var foodOrder = mapper.readValue(message,FoodOrder.class);
		if(foodOrder.getAmount() > MAX_AMOUNT_ORDER) {
			throw new IllegalArgumentException("Food order amount is too long");			
		}
		logger.info("Food Order s valid : {}", foodOrder);
		
	}

}
