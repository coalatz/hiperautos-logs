package com.log_injestor.service1.service;

import com.log_injestor.service1.config.RabbitMQConfigService1;
import com.log_injestor.service1.model.LogtDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogProducerService {

    @Autowired
	private RabbitTemplate rabbitTemplate;

	public static final String INJESTOR_ROUTING_KEY = "service1.new.log";
	
	public void sendLogMessage(LogtDTO message) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfigService1.LOG_EXCHANGE_INJESTOR,
                INJESTOR_ROUTING_KEY,
                message
        );
        System.out.println("log recebido e enviado para o analyzer");
	}
}	
