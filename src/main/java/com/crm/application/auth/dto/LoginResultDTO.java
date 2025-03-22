package com.crm.application.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties({"menuNavigation"})
public class LoginResultDTO {
    private String AccessToken;
    private String RefreshToken;
    private String UserId;
    private String Email;
    private String FirstName;
    private String LastName;
    private String CompanyName;
    private String Avatar;
    private List<String> Roles;

    public LoginResultDTO(String accessToken, String refreshToken, String userId, String email, String firstName, String lastName, String companyName, String avatar, List<String> roles) {
        AccessToken = accessToken;
        RefreshToken = refreshToken;
        UserId = userId;
        Email = email;
        FirstName = firstName;
        LastName = lastName;
        CompanyName = companyName;
        Avatar = avatar;
        Roles = roles;
    }

    public LoginResultDTO() {
    }
}
