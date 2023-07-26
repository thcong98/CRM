package com.example.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

<<<<<<< HEAD
@SpringBootApplication (exclude = {SecurityAutoConfiguration.class})
=======
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "DG8-Intern-TMA-Project",
		version = "1.0.1",
		description = "This is a Project API"
	)
)
>>>>>>> 4e37594ce8c8bea6ee7703d1c892bcbbd0e32ea2
public class CrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

}
