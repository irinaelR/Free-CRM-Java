package com.crm.application.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ApiClient {

    @Autowired
    @Qualifier("authenticatedRestTemplate")
    private RestTemplate restTemplate;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    // Example method to call an API endpoint
    public <T> T get(String endpoint, Class<T> responseType) {
        String url = apiBaseUrl + endpoint;
        return restTemplate.getForObject(url, responseType);
    }

    // Add methods for POST, PUT, DELETE etc.
}