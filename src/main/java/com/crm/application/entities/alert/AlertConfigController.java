package com.crm.application.entities.alert;

import com.crm.application.common.ApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/AlertConfig")
public class AlertConfigController {
    private final ApiClient apiClient;

    public AlertConfigController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("")
    public AlertConfig getAlertConfig() {
        return AlertConfig.getAlertConfig(apiClient);
    }

    @PostMapping("Update")
    public String updateAlertConfig(@RequestBody AlertConfig alertConfig) throws JsonProcessingException {
        return AlertConfig.updateAlertConfig(alertConfig, apiClient);
    }
}
