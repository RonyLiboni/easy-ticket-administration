package com.easyticket.corebusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;

@SpringBootApplication
@EnableFeignClients
@EnableEurekaClient
@OpenAPIDefinition(servers = { @Server(url = "http://localhost:8080/core-business-ms", description = "Gateway URL") })
public class CoreBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreBusinessApplication.class, args);
	}

}
