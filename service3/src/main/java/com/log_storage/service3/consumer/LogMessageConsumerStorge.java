package com.log_storage.service3.consumer;

import com.log_storage.service3.model.LogAnalysisResponse;
import com.log_storage.service3.repository.StorageRepository;
import org.springframework.stereotype.Component;
import com.log_storage.service3.config.RabbitMQConsumerConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

@Component
public class LogMessageConsumerStorge {

    private final StorageRepository repository;

    public LogMessageConsumerStorge(StorageRepository repository) {
        this.repository = repository;
    }

    @RabbitListener(queues = RabbitMQConsumerConfig.LOG_QUEUE_STORAGE)
    public void receiveMessage(LogAnalysisResponse logMessage) {
        try{
            repository.save(logMessage);
            System.out.println("-----------------------------------");
            System.out.println("✅ DADOS SALVOS NO POSTGRESQL!");
            System.out.println("Status: " + logMessage.getStatus());
            System.out.println("Categoria: " + logMessage.getCategory());
            System.out.println("Log Original: " + logMessage.getOriginalLog());
            System.out.println("-----------------------------------");
        }catch (Exception e) {
            System.err.println("❌ ERRO AO PERSISTIR LOG NO DB: " + e.getMessage());
            throw new RuntimeException("Falha ao salvar no banco. Reenfileirando mensagem...", e);
        }
    }
}

