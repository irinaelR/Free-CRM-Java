package com.crm.application.auth.services;

import com.crm.application.auth.dto.LoginResultDTO;
import com.crm.application.auth.services.interfaces.TokenProvider;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Setter
@Component
public class AuthTokenProvider implements TokenProvider {
    private String accessToken;
    private String refreshToken;

    @Lazy
    @Autowired
    private AuthService authService;

    @Override
    public String getToken() {
        return this.accessToken;
    }

    @Override
    public void setToken(String token) {
        this.accessToken = token;
    }

    @Override
    public String getRefreshToken() {
        return this.refreshToken;
    }

    @Override
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public boolean refreshToken() {
        if (refreshToken == null || refreshToken.isEmpty()) {
            return false;
        }

        try {
            LoginResultDTO result = authService.refreshToken(this.refreshToken);
            if (result != null && result.getAccessToken() != null) {
                this.accessToken = result.getAccessToken();

                // Update refresh token if a new one is provided
                if (result.getRefreshToken() != null) {
                    this.refreshToken = result.getRefreshToken();
                }

                return true;
            }
            return false;
        } catch (Exception e) {
            // Token refresh failed
            return false;
        }
    }
}
