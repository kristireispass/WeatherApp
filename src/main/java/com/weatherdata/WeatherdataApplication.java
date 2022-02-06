package com.weatherdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WeatherdataApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherdataApplication.class, args);
	}

}
