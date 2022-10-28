package com.easyticket.corebusiness;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
//@EnableEurekaClient
public class CoreBusinessApplication {

	public static void main(String[] args) {
		SpringApplication.run(CoreBusinessApplication.class, args);
	}

}
