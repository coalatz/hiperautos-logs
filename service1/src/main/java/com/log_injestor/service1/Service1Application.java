package com.log_injestor.service1;

import com.log_injestor.service1.service.LogProducerService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Service1Application {

    public static void main(String[] args) {
        SpringApplication.run(Service1Application.class, args);
    }

    @Bean
    public CommandLineRunner runner(LogProducerService logProducerService) {
        return args -> {
            // 🚨 AQUI VOCÊ CHAMA O MÉTODO DE ENVIO DIRETAMENTE
            System.out.println(">>> Tentando enviar mensagem de teste via CommandLineRunner...");

            logProducerService.sendLogMessage("INFO: Usuário 123 logou com sucesso.");

            logProducerService.sendLogMessage("ERROR: Falha de conexão com o banco de dados.");
        };

    }

}
