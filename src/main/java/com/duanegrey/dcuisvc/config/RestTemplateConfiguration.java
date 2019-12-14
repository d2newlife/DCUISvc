package com.duanegrey.dcuisvc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {
    private final CAppProperties appProperties;

    @Autowired
    public RestTemplateConfiguration(CAppProperties appProperties) {
        this.appProperties = appProperties;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder, CAppProperties appProperties) {
        Duration durationConn = Duration.ofSeconds(appProperties.getLgConnectTimeout());
        Duration durationRead = Duration.ofSeconds(appProperties.getLgReadTimeout());
        return restTemplateBuilder.setConnectTimeout(durationConn).setReadTimeout(durationRead).build();
    }
}
