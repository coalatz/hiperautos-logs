package com.log_analyzer.service2.config;
import org.springframework.amqp.core.Queue;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Binding;

@Configuration
public class RabbitMQConsumerConfig {

    public static final String LOG_QUEUE_ANALYZER = "analyzer-logs-queue";

    public static final String LOG_EXCHANGE_INJESTOR = "log-injestor";

    public static final String LOG_ROUTING_KEY = "service1.new.log";

    @Bean
    public Queue analyzerQueue() {
        return new Queue(LOG_QUEUE_ANALYZER, true);
    }

    @Bean
   public DirectExchange exchange() {
        return new DirectExchange(LOG_EXCHANGE_INJESTOR, true, false);
   }

    @Bean
   public Binding analyzerBinding(Queue analyzerQueue, DirectExchange exchange) {
        return BindingBuilder.bind(analyzerQueue)
                .to(exchange)
                .with(LOG_ROUTING_KEY);
   }



}