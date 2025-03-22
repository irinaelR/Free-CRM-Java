package com.crm.application.config;

import java.util.Collections;

import com.crm.application.interceptors.AuthInterceptor;
import com.crm.application.auth.services.interfaces.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate plainRestTemplate() {
        return new RestTemplate();
    }

    @Bean
    public RestTemplate authenticatedRestTemplate(TokenProvider tokenProvider) {
        RestTemplate restTemplate = new RestTemplate();

        // Add auth interceptor that uses TokenProvider
        restTemplate.setInterceptors(Collections.singletonList(
                new AuthInterceptor(tokenProvider)
        ));

        return restTemplate;
    }
}