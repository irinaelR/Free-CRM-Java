package com.crm.application.auth.services;

import com.crm.application.auth.dto.LoginRequestDTO;
import com.crm.application.auth.dto.LoginResultDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@Getter
public class AuthService {
    @Autowired
    private RestTemplate plainRestTemplate;

    @Autowired
    private AuthTokenProvider tokenProvider;

    @Value("${api.base-url}")
    private String apiBaseUrl;

    private String accessToken;

    public LoginResultDTO login(String username, String password) {
        // Create the login request
        LoginRequestDTO loginRequest = new LoginRequestDTO(username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequestDTO> entity = new HttpEntity<>(loginRequest, headers);

        // Make the login request with the plain RestTemplate
        ResponseEntity<String> result = plainRestTemplate.postForEntity(
                apiBaseUrl + "/Security/Login",
                entity,
                String.class
        );

        return getLoginResultDTO(result);
    }

    public LoginResultDTO refreshToken(String refreshToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(refreshToken, headers);

        // Make the login request with the plain RestTemplate
        ResponseEntity<String> result = plainRestTemplate.postForEntity(
                apiBaseUrl + "/Security/RefreshToken",
                entity,
                String.class
        );

        return getLoginResultDTO(result);

    }

    private LoginResultDTO getLoginResultDTO(ResponseEntity<String> result) {
        String rawJson = result.getBody();
        LoginResultDTO loginResult = null;

        if (rawJson != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                JsonNode rootNode = mapper.readTree(rawJson);
                JsonNode dataNode = rootNode.path("content").path("data");

                // Convert data node to LoginResultDTO
                loginResult = mapper.treeToValue(dataNode, LoginResultDTO.class);

                // Store both tokens for later use
                if (loginResult != null) {
                    tokenProvider.setToken(loginResult.getAccessToken());
                    tokenProvider.setRefreshToken(loginResult.getRefreshToken());
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to parse response", e);
            }
        }
        return loginResult;
    }

}
