package com.log_injestor.service1.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String LOG_EXCHANGE_INJESTOR = "log-injestor"; 

    @Bean
    DirectExchange exchange() {
        return new DirectExchange(LOG_EXCHANGE_INJESTOR, true, false); 
    }
}