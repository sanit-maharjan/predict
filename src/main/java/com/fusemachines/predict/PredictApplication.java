package com.fusemachines.predict;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableScheduling
public class PredictApplication {

	public static void main(String[] args) {
		SpringApplication.run(PredictApplication.class, args);
	}
	
	@RequestMapping("/hello")
	public String hello() {
		return "hello";
	}
}
