package io.github.starbank.cartoes;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
@Slf4j
public class CartoesApplication {

	public static void main(String[] args) {
		log.info("Informação: {}", "Da para colocar strings");
		log.info("Informação: {} , {}", "Da para colocar numeros", 1);
		log.info("Informação: {} , {}", "Da para colocar classes", new Object());
		log.error("Erro: {}", "Teste Erro");
		log.warn("Aviso: {}", "Teste Aviso");
		SpringApplication.run(CartoesApplication.class, args);
	}

}
