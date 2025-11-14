package com.log_analyzer.service2.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.log_analyzer.service2.config.RabbitMQConsumerConfig;

@Component
public class LogMessageConsumer {

	@RabbitListener(queues = RabbitMQConsumerConfig.LOG_QUEUE_ANALYZER)
    public void receiveMessage(String logMessage) {
        System.out.println("-----------------------------------");
        System.out.println(" [x] LOG RECEBIDO para ANÁLISE: ");
        System.out.println("     Conteúdo: " + logMessage);
        System.out.println("-----------------------------------");

        if (logMessage.contains("ERROR")) {
            System.out.println("     🚨 ALERTA: ERRO GRAVE DETECTADO!");
        }
    }


}
