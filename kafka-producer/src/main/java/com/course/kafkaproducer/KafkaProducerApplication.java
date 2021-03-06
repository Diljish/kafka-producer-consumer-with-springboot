package com.course.kafkaproducer;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.course.kafkaproducer.entity.FoodOrder;
import com.course.kafkaproducer.entity.SimpleNumber;
import com.course.kafkaproducer.producer.FoodOrderProducer;
import com.course.kafkaproducer.producer.ImageProducer;
import com.course.kafkaproducer.producer.InvoiceProducer;
import com.course.kafkaproducer.producer.SimpleNumberProducer;
import com.course.kafkaproducer.service.ImageService;
import com.course.kafkaproducer.service.InvoiceService;




@SpringBootApplication
//@EnableScheduling
public class KafkaProducerApplication implements CommandLineRunner {

	// @Autowired
	// private HelloKafkaProducer helloKafkaProducer;
	
//	@Autowired
//	private KafkaKeyProducer kafkaKeyProducer;
//	@Autowired
//	private EmployeeJsonProducer employeeJsonProducer;
//	@Autowired
//	private FoodOrderProducer foodOrderProducer;
	
//	@Autowired
//	private SimpleNumberProducer simpleNumberProducer;
//	@Autowired
//	private ImageService imageService;
//	@Autowired
//	private ImageProducer imageProducer;
	
	@Autowired
	private InvoiceService invoiceService;
	@Autowired
	private InvoiceProducer invoiceProducer;
	
	
	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		// helloKafkaProducer.sendHello("Diljish " +Math.random());
//		for(int i=0;i<30;i++) {
//			var key = "key-"+(i%4);
//			var data = "data "+i+" with key"+key;
//			kafkaKeyProducer.sendMessage(key, data);
//		}
		
//		for(int i=0;i<5;i++) {
//			var employee = new Employee("emp"+i, "employee"+i, LocalDate.now());
//			employeeJsonProducer.sendMessage(employee);
//			
//		}
		
//		var chickenOrder = new FoodOrder(3, "chicken");
//		var fishOrder = new FoodOrder(10, "fish");
//		var pizza = new FoodOrder(5,"pizza");
//		
//		foodOrderProducer.sendMessage(chickenOrder);
//		foodOrderProducer.sendMessage(fishOrder);
//		foodOrderProducer.sendMessage(pizza);
		
//		for(int i=100;i< 103;i++) {
//			
//			var simpleNumber = new SimpleNumber(i);
//			simpleNumberProducer.sendMessage(simpleNumber);
//			
//		}
		
//		var image1 = imageService.generateImage("jpg");
//		var image2 = imageService.generateImage("svg");
//		var image3 = imageService.generateImage("png");
//		
//		imageProducer.send(image1);
//		imageProducer.send(image2);
//		imageProducer.send(image3);
		
		
		for(int i=0;i<10;i++) {
			var newInvoice = invoiceService.generateInvoice();
			
			if(i>= 5) {
				newInvoice.setAmount(-1);
			}
			invoiceProducer.send(newInvoice);
		}
		
	}

}
