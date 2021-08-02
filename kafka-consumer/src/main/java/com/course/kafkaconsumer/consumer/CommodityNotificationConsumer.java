package com.course.kafkaconsumer.consumer;

import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Commodity;

@Service
public class CommodityNotificationConsumer {
	private ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(CommodityNotificationConsumer.class);
	
	@KafkaListener(topics = "t_commodity" , groupId = "cg-notification")
	public void consume(String message) throws JsonMappingException, JsonProcessingException, InterruptedException {
		var commodity = objectMapper.readValue(message, Commodity.class);
		Thread.sleep(ThreadLocalRandom.current().nextLong(500,1000));
		logger.info("Notification logic for {} ", commodity);
		
	}

}
