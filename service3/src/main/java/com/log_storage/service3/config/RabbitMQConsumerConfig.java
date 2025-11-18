package com.log_storage.service3.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConsumerConfig {
    public static final String LOG_QUEUE_STORAGE = "storage-logs-queue";
    public static final String LOG_EXCHANGE_ANALYZER = "log-analyzer";
    public static final String ROUTING_KEY_ANALYZER = "service2.analyzer.response";

    @Bean
    public Queue storageQueue() {
        return new Queue(LOG_QUEUE_STORAGE, true);
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange( LOG_EXCHANGE_ANALYZER, true, false);
    }

    @Bean
    public Binding storageBinding(Queue storageQueue, DirectExchange exchange) {
        return BindingBuilder.bind(storageQueue)
                .to(exchange)
                .with(ROUTING_KEY_ANALYZER);
    }
}
