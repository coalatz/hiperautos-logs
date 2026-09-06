package com.hiperautos.logservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Configura o pool de threads para processamento assíncrono de logs.
 *
 * AbortPolicy: quando o pool está cheio (20 threads) E a fila interna está cheia
 * (200 itens), lança RejectedExecutionException. O controller captura isso e
 * retorna 503 ao cliente, sendo honesto sobre a sobrecarga — em vez de bloquear
 * a thread HTTP do Tomcat (comportamento de CallerRunsPolicy).
 */
@Configuration
public class AsyncConfig {

    @Value("${app.async.core-pool-size:5}")
    private int corePoolSize;

    @Value("${app.async.max-pool-size:20}")
    private int maxPoolSize;

    @Value("${app.async.queue-capacity:200}")
    private int queueCapacity;

    @Bean(name = "logProcessingExecutor")
    public Executor logProcessingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("log-async-");
        // Rejeita e lança exceção quando cheio → controller retorna 503
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        executor.initialize();
        return executor;
    }
}
