package io.github.starbank.cartoes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;

@SpringBootApplication
@EnableEurekaClient
@EnableRabbit
public class CartoesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CartoesApplication.class, args);
	}

}
