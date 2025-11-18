package com.log_analyzer.service2.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfig {

    public static final String LOG_EXCHANGE_ANALYZER = "log-analyzer";

    @Bean
    public DirectExchange exchangeProducer() {
        return new DirectExchange(LOG_EXCHANGE_ANALYZER, true, false);
    }
}
