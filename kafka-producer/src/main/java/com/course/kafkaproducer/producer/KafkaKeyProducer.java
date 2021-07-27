package com.course.kafkaproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaKeyProducer {
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public void sendMessage(String key, String message) {
		kafkaTemplate.send("t_multipartitions", key, message);
	}
	
	
	
	

}
