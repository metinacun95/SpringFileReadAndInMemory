package com.metinacun.springfileupload;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringFileUploadApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringFileUploadApplication.class, args);
	}

}
