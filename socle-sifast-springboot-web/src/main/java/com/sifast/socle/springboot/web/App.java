package com.sifast.socle.springboot.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EntityScan("com.sifast.socle.springboot.model")
@SpringBootApplication
@EnableJpaRepositories("com.sifast.socle.springboot.dao")
@ComponentScan(basePackages = {"com.sifast.socle.springboot"})
public class App {

	public static void main(String[] args) {

		SpringApplication.run(App.class, args);
	}

}
