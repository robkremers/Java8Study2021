package com.javainaction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.javainaction.javacodegeeks"})
public class JavainactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavainactionApplication.class, args);
	}

}
