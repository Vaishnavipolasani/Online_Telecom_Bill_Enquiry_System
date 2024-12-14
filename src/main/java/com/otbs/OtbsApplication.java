package com.otbs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.otbs")
public class OtbsApplication {

	public static void main(String[] args) {
		SpringApplication.run(OtbsApplication.class, args);
	}

}
