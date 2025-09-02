package com.example.transfer_service.config;

import com.ledger.openapi.ApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiClientConfig {
    @Bean
    public ApiClient apiClient() {
        //apiClient.setBasePath("http://localhost:8081/");
        return new ApiClient();
    }
}
