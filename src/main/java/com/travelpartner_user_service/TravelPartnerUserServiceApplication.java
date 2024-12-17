package com.travelpartner_user_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.travelpartner_user_service")
@ComponentScan(basePackages = "com.travelpartner.user_service")
@EnableJpaRepositories(basePackages = "com.travelpartner.user_service.repository")
@EntityScan(basePackages = "com.travelpartner.user_service.entity")
public class TravelPartnerUserServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TravelPartnerUserServiceApplication.class, args);
		
		System.out.println("Application started!");
	}

}
