package com.crm.application.interceptors;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

import com.crm.application.auth.services.interfaces.TokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
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
            // Create a custom response that will trigger a redirect on the frontend
            return new ClientHttpResponse() {
                @Override
                public HttpStatusCode getStatusCode() throws IOException {
                    return HttpStatusCode.valueOf(401); // Unauthorized
                }

                @Override
                public String getStatusText() throws IOException {
                    return "Unauthorized";
                }

                @Override
                public void close() {
                    // Nothing to close
                }

                @Override
                public InputStream getBody() throws IOException {
                    // Create a JSON response body with information that your frontend can use
                    String responseBody = "{\"error\":\"auth_failure\",\"message\":\"Authentication failed\",\"redirect\":\"/Login\"}";
                    return new ByteArrayInputStream(responseBody.getBytes(StandardCharsets.UTF_8));
                }

                @Override
                public HttpHeaders getHeaders() {
                    HttpHeaders headers = new HttpHeaders();
                    headers.setContentType(MediaType.APPLICATION_JSON);
                    return headers;
                }
            };
        }
    }

}
