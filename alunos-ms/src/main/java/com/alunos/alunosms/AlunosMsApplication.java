package com.alunos.alunosms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class AlunosMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AlunosMsApplication.class, args);
	}

}
