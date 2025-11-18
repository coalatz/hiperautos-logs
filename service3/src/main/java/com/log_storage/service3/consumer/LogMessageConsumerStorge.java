package com.log_storage.service3.consumer;

import org.springframework.stereotype.Component;
import com.log_storage.service3.config.RabbitMQConsumerConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class LogMessageConsumerStorge {

    @RabbitListener(queues = RabbitMQConsumerConfig.LOG_QUEUE_STORAGE)
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

