package com.course.kafkaconsumer.consumer;

import java.net.http.HttpConnectTimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Image;

public class ImageConsumer {
	
	private Logger logger = LoggerFactory.getLogger(ImageConsumer.class);
	private ObjectMapper objectMapper = new ObjectMapper();
	@KafkaListener(topics = "t_image", containerFactory = "imageRetryContainerFactory")
	public void consume(String message) throws JsonMappingException, JsonProcessingException, HttpConnectTimeoutException {
		var image = objectMapper.readValue(message, Image.class);
		if(image.getType().equalsIgnoreCase("svg")) {
			throw new HttpConnectTimeoutException("simulate failed api call");
			
		}
		logger.info("image processing:{}", image);
		
	}

}
