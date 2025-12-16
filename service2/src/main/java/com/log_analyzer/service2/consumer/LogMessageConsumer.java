package com.log_analyzer.service2.consumer;

import com.log_analyzer.service2.model.LogAnalysisResponse;
import com.log_analyzer.service2.service.LogAnalyzerAIService;
import com.log_analyzer.service2.service.LogProducerServiceAnalyzer;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.log_analyzer.service2.config.RabbitMQConsumerConfig;

@Component
public class LogMessageConsumer {

    private final LogAnalyzerAIService iaService;
    private final LogProducerServiceAnalyzer producer;

    public LogMessageConsumer(LogAnalyzerAIService iaService, LogProducerServiceAnalyzer producer) {
        this.iaService = iaService;
        this.producer = producer;
    }

	@RabbitListener(queues = RabbitMQConsumerConfig.LOG_QUEUE_ANALYZER)
    public void receiveMessage(String logMessage) {
        LogAnalysisResponse response = iaService.sendLogAI(logMessage);

        if (response != null) {
            System.out.println("--------------------------------------------");
            System.out.println("✅ ANALISE DA IA COMPLETADA!");
            System.out.println("STATUS: " + response.getStatus());
            System.out.println("CATEGORIA: " + response.getCategory());
            System.out.println("RESUMO: " + response.getSummary());
            System.out.println("LOG ORIGINAL: " + response.getOriginalLog());

            System.out.println("[x] Sent Analysis");
            producer.sendLogMenssage(response);
        }
        else  {
            System.out.println("     🚨 ALERTA: ERRO GRAVE DETECTADO!");
        }
    }


}
