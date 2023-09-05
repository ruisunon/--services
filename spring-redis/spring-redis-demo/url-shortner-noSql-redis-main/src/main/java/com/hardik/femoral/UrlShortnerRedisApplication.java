package com.hardik.femoral;

import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

@SpringBootApplication
public class UrlShortnerRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(UrlShortnerRedisApplication.class, args);
	}

	@Bean
	@Primary
	public UrlValidator urlValidator() {
		return new UrlValidator(new String[] { "http", "https" });
	}
}
