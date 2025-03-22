package com.crm.application.auth.services.interfaces;

public interface TokenProvider {
    String getToken();
    void setToken(String token);
    boolean refreshToken();
    String getRefreshToken();
    void setRefreshToken(String refreshToken);
}
