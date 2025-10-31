package com.techpro.upc.reviews_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ReviewsServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReviewsServiceApplication.class, args);
	}

}
