package com.crm.application.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

//    public <T> T get(String endpoint, Class<T> responseType) {
//        String url = apiBaseUrl + endpoint;
//
//        ResponseEntity<Map> responseEntity = restTemplate.exchange(
//                url,
//                HttpMethod.GET,
//                null,
//                Map.class
//        );
//
//        Map response = responseEntity.getBody();
//        if (response != null && response.containsKey("content")) {
//            Map content = (Map) response.get("content");
//            if (content != null && content.containsKey("data")) {
//                Map<String, Object> data = (Map<String, Object>) content.get("data");
//                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
//                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//                return mapper.convertValue(data, responseType);
//            }
//        }
//        return null;
//    }

    public <T> List<T> getList(String endpoint, Class<T> responseType) {
        String url = apiBaseUrl + endpoint;

        ResponseEntity<Map> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                Map.class
        );

        Map response = responseEntity.getBody();
        if (response != null && response.containsKey("content")) {
            Map content = (Map) response.get("content");
            if (content != null && content.containsKey("data")) {
                List<Map<String, Object>> dataList = (List<Map<String, Object>>) content.get("data");
                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

                return dataList.stream()
                        .map(map -> mapper.convertValue(map, responseType))
                        .collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }

    public <T> String post(String endpoint, T body) throws JsonProcessingException {
        String url = apiBaseUrl + endpoint;
        // System.out.println("Request body: " + body.toString());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String jsonBody = mapper.writeValueAsString(body);

        HttpEntity<String> requestEntity = new HttpEntity<>(jsonBody, headers);
        return restTemplate.postForObject(url, requestEntity, String.class);
    }
}