package com.course.kafkaconsumer.consumer;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Commodity;

@Service
public class CommodityDashboardConsumer {
	private ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CommodityDashboardConsumer.class);
	
	@KafkaListener(topics = "t_commodity" , groupId = "cg-dashboard")
	public void consume(String message) throws JsonMappingException, JsonProcessingException {
		var commodity = objectMapper.readValue(message, Commodity.class);
		logger.info("Dashboard logic for {} ", commodity);
		
		
		
	}

}
