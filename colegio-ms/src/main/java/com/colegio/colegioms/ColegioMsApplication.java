package com.colegio.colegioms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
public class ColegioMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ColegioMsApplication.class, args);
	}

}
