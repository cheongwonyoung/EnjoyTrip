package com.ssafy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.ssafy"})
public class EnjoytripSpringbootTeam6Application {

	public static void main(String[] args) {
		SpringApplication.run(EnjoytripSpringbootTeam6Application.class, args);
	}

}
