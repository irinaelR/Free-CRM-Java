package com.crm.application.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDTO {
    public LoginRequestDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    private String email;
    private String password;
}
