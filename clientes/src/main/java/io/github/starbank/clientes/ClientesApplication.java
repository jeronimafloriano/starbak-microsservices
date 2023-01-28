package io.github.starbank.clientes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ClientesApplication {

	public static void main(String[] args) {
		SpringApplication.run(io.github.starbank.clientes.ClientesApplication.class, args);
	}

}
