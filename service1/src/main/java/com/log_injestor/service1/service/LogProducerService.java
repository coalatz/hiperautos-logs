package com.log_injestor.service1.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.log_injestor.service1.config.RabbitMQConfig;

public class LogProducerService {

	private RabbitTemplate rabbitTemplate;
	public static final String INJESTOR_ROUTING_KEY = "service1.new.log";
	
	
	public LogProducerService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendLogmessage(String message) {
		rabbitTemplate.convertAndSend(
				RabbitMQConfig.LOG_EXCHANGE_NAME,
				INJESTOR_ROUTING_KEY,
				message
				);
		System.out.println(" [x] Sent '" + message + "' with routing key '" + INJESTOR_ROUTING_KEY + "'");
	}
}	
