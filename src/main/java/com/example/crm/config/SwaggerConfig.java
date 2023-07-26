package com.example.crm.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI usersMicroserviceOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("CRM APIs document")
                                .description("CRM Customer Touchpoint project" +
                                        " Batch13BD - DG8 - Java BE")
                        .version("1.0")
                );
    }
}
