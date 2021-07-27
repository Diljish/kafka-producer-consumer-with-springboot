package com.course.kafkaproducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.course.kafkaproducer.producer.HelloKafkaProducer;
import com.course.kafkaproducer.producer.KafkaKeyProducer;

@SpringBootApplication
//@EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {

	// @Autowired
	// private HelloKafkaProducer helloKafkaProducer;
	
	@Autowired
	private KafkaKeyProducer kafkaKeyProducer;
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// helloKafkaProducer.sendHello("Diljish " +Math.random());
		for(int i=0;i<30;i++) {
			var key = "key-"+(i%4);
			var data = "data "+i+" with key"+key;
			kafkaKeyProducer.sendMessage(key, data);
		}
		
	}

}
