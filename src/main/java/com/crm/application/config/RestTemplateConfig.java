package com.crm.application.config;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.crm.application.interceptors.AuthInterceptor;
import com.crm.application.auth.services.interfaces.TokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
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
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());

        ClientHttpRequestInterceptor loggingInterceptor = (request, body, execution) -> {
            System.out.println("Request URI: " + request.getURI());
            System.out.println("Request Method: " + request.getMethod());
            System.out.println("Request Headers: " + request.getHeaders());
            System.out.println("Request Body: " + new String(body, StandardCharsets.UTF_8));
            return execution.execute(request, body);
        };

        // Get existing interceptors or create a new list
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>(restTemplate.getInterceptors());

        // Add both interceptors
        interceptors.add(loggingInterceptor);
        interceptors.add(new AuthInterceptor(tokenProvider));

        // Set the combined interceptors back to RestTemplate
        restTemplate.setInterceptors(interceptors);


        return restTemplate;
    }
}