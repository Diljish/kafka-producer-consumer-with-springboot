package com.course.kafkaconsumer.consumer;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;

import entity.Employee;

@Service
public class EmployeeJsonConsumer {
	
	private ObjectMapper objectMapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(EmployeeJsonConsumer.class);
	
	@KafkaListener(topics ="t_employee")
	public void consume(String message) throws Exception{
		
		var employee = objectMapper.readValue(message, Employee.class);
		logger.info("Employye : {}" +employee);
		
	}
	

}
