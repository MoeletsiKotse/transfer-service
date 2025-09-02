package com.example.transfer_service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.ledger.openapi", "com.example.transfer_service", })
public class AppConfig {
}
