package com.crm.application.auth.controllers;

import com.crm.application.auth.dto.LoginRequestDTO;
import com.crm.application.auth.dto.LoginResultDTO;
import com.crm.application.auth.services.AuthService;
import com.crm.application.common.ApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/Auth")
public class AuthController {

    private final AuthService authService;
    private final ApiClient apiClient;

    public AuthController(AuthService authService, ApiClient apiClient) {
        this.authService = authService;
        this.apiClient = apiClient;
    }

    @PostMapping("/Login")
    public LoginResultDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
        return authService.login(loginRequestDTO.getEmail(), loginRequestDTO.getPassword());
    }

//    @GetMapping("/Test")
//    public String test() {
//        String endpoint = "/Budget/GetBudgetList";
//        return apiClient.get(endpoint, String.class);
//    }
}
