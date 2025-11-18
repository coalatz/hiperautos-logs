package com.log_analyzer.service2.service;

import com.log_analyzer.service2.config.RabbitMQProducerConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class LogProducerServiceAnalyzer {

    private RabbitTemplate rabbitTemplate;
    public static final String ROUTING_KEY_ANALYZER = "service2.analyzer.response";

    public LogProducerServiceAnalyzer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendLogMenssage(String message) {
        rabbitTemplate.convertAndSend(
               RabbitMQProducerConfig.LOG_EXCHANGE_ANALYZER,
                ROUTING_KEY_ANALYZER,
                message

        );
        System.out.println(" [x] Sent '" + message + "' with routing key '" + ROUTING_KEY_ANALYZER + "'");
    }
}
