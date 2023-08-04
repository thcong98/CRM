package com.example.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
//use SwaggerConfig class to config Swagger
//@OpenAPIDefinition(
//	info = @Info(
//		title = "DG8-Intern-TMA-Project",
//		version = "1.0.1",
//		description = "This is a Project API"
//	)
//)
public class CrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

}
