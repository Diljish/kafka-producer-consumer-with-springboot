package com.course.kafkaproducer.producer.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.course.kafkaproducer.entity.CarLocation;
import com.course.kafkaproducer.producer.CarLocationProducer;


@Service
public class CarLocationScheduler {
	
	private static final Logger logger = LoggerFactory.getLogger(CarLocationScheduler.class);
	
	private CarLocation carOne;
	private CarLocation carTwo;
	private CarLocation carThree;
	
	@Autowired
	private CarLocationProducer carLocationProducer;
	
	public CarLocationScheduler() {
		var now = System.currentTimeMillis();
		carOne = new CarLocation("car-one", now, 0);
		carTwo = new CarLocation("car-two", now, 110);
		carThree = new CarLocation("car-three", now, 95);
		
	}
	
	@Scheduled(fixedRate = 10000)
	public void generateCarLocation() {
		var now = System.currentTimeMillis();
		carOne.setTimestamp(now);
		carTwo.setTimestamp(now);
		carThree.setTimestamp(now);
		
		carOne.setDistance(carOne.getDistance() +1);
		logger.info("carOne: {} ", carOne);
		carTwo.setDistance(carTwo.getDistance() -1);
		logger.info("carTwo: {} ", carTwo);
		carThree.setDistance(carThree.getDistance() +1);
		logger.info("carThree: {} ", carThree);
		
		try {
			carLocationProducer.sendMessage(carOne);
			carLocationProducer.sendMessage(carTwo);
			carLocationProducer.sendMessage(carThree);
		}catch (Exception e) {
			logger.warn("Error happend: {} ", e);
		}
	}
	

}
