package com.example.crm;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
	info = @Info(
		title = "CRM APIs document",
		version = "1.0.1",
		description = "CRM Customer Touch Point project" +
				" Batch13BD - DG8 - Java BE"
	)
)
@SecurityScheme(
		name = "Authorization",
		scheme = "Bearer",
		type = SecuritySchemeType.HTTP,
		in = SecuritySchemeIn.HEADER
)

public class CrmApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrmApplication.class, args);
	}

}
