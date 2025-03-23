package com.crm.application.interceptors;

import java.io.IOException;
import java.util.function.Supplier;

import com.crm.application.auth.services.interfaces.TokenProvider;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;


public class AuthInterceptor implements ClientHttpRequestInterceptor {
    private final TokenProvider tokenProvider;

    public AuthInterceptor(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body,
                                        ClientHttpRequestExecution execution) throws IOException {
        // Apply current token
        applyToken(request);

        try {
            ClientHttpResponse response = execution.execute(request, body);

            // Check for unauthorized (401) or token expired (can be 401 or 498)
            if (response.getStatusCode().value() == 401 || response.getStatusCode().value() == 498) {
                return handleTokenExpiration(request, body, execution);
            }

            return response;
        } catch (Exception e) {
            // Handle exceptions that might indicate auth issues
            if (e.getMessage().contains("unauthorized") || e.getMessage().contains("forbidden")) {
                return handleTokenExpiration(request, body, execution);
            }
            throw e;
        }
    }

    private void applyToken(HttpRequest request) {
        String token = tokenProvider.getToken();
        if (token != null && !token.isEmpty()) {
            request.getHeaders().set("Authorization", "Bearer " + token);
        }
    }

    private ClientHttpResponse handleTokenExpiration(HttpRequest request, byte[] body,
                                                     ClientHttpRequestExecution execution) throws IOException {
        // Try to refresh the token
        boolean refreshed = tokenProvider.refreshToken();

        if (refreshed) {
            // Create a new request since the original might not be reusable
            applyToken(request);
            return execution.execute(request, body);
        } else {
            // If refresh fails, let the calling code handle the authentication failure
            throw new IOException("Authentication failed - unable to refresh token");
        }
    }


}
