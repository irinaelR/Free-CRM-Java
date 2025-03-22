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
        applyToken(request);

        ClientHttpResponse response = execution.execute(request, body);

        // Handle token expiration (status code 498)
        if (response.getStatusCode().value() == 498) {

            // Try to refresh the token
            boolean refreshed = tokenProvider.refreshToken();

            if (refreshed) {

                // Apply the new token and retry the request
                applyToken(request);
                return execution.execute(request, body);
            } else {
                System.out.println("Token refresh failed.");
            }
        }

        return response;
    }

    private void applyToken(HttpRequest request) {
        String token = tokenProvider.getToken();
        if (token != null && !token.isEmpty()) {
            request.getHeaders().set("Authorization", "Bearer " + token);
        } 
    }


}
