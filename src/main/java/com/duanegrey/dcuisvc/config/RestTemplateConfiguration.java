package com.duanegrey.dcuisvc.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class RestTemplateConfiguration {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        Duration durationTime = Duration.ofSeconds(10);
        return restTemplateBuilder.setConnectTimeout(durationTime).setReadTimeout(durationTime).build();
    }
}
