package com.personal.app;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.partyh.finder.common", "com.personal.app"})
@EntityScan("com.personal.app.models.entities")
public class PartyhFinderMissiveApplication {

	public static void main(String[] args) {
		SpringApplication.run(PartyhFinderMissiveApplication.class, args);
	}

}
