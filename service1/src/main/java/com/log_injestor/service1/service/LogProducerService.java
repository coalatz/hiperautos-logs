package com.log_injestor.service1.service;

import com.log_injestor.service1.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducerService {

	private RabbitTemplate rabbitTemplate;
	public static final String INJESTOR_ROUTING_KEY = "service1.new.log";
	
	
	public LogProducerService(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}
	
	public void sendLogMessage(String message) {
		rabbitTemplate.convertAndSend(
				RabbitMQConfig.LOG_EXCHANGE_INJESTOR,
				INJESTOR_ROUTING_KEY,
				message
				);
		System.out.println(" [x] Sent '" + message + "' with routing key '" + INJESTOR_ROUTING_KEY + "'");
	}
}	
