package com.log_analyzer.service2;

import com.log_analyzer.service2.service.LogProducerServiceAnalyzer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@EnableRabbit
@SpringBootApplication
public class Service2Application {

	public static void main(String[] args) {
		SpringApplication.run(Service2Application.class, args);
	}

    @Bean
    public CommandLineRunner runner(LogProducerServiceAnalyzer logProducerService) {
        return args -> {
            // 🚨 AQUI VOCÊ CHAMA O MÉTODO DE ENVIO DIRETAMENTE
            System.out.println(">>> Tentando enviar mensagem de teste via CommandLineRunner...");

            logProducerService.sendLogMenssage("INFO: Usuário 123 logou com sucesso.");
            logProducerService.sendLogMenssage("ERROR");
           
        };

    }

}
