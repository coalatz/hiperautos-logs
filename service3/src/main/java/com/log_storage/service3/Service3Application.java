package com.log_storage.service3;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class Service3Application {

	public static void main(String[] args) {
		SpringApplication.run(Service3Application.class, args);
	}

}
