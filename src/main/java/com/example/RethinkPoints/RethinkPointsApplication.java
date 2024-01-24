package com.example.RethinkPoints;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableJpaRepositories
@EnableScheduling
@EnableFeignClients
public class RethinkPointsApplication {

	public static void main(String[] args) {
		SpringApplication.run(RethinkPointsApplication.class, args);
	}

}
