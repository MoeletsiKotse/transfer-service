package com.example.transfer_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI swaggerAPI(){
        return new OpenAPI().info(new Info().title("Transfer API")
                .description("Transfer Service")
                .version("1.0"));
    }
}
