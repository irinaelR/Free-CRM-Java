package com.crm.application.entities.alert;

import com.crm.application.common.ApiClient;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AlertConfig {
    String id;
    double percentage;
    LocalDateTime dateAdded;
    LocalDateTime dateUpdated;

    public static AlertConfig getAlertConfig(ApiClient apiClient) {
        return apiClient.get("/AlertConfig/GetAlertConfig", AlertConfig.class);
    }

    public static String updateAlertConfig(AlertConfig alertConfig, ApiClient apiClient) throws JsonProcessingException {
        AlertConfigRequest request = new AlertConfigRequest();
        request.setPercentage(alertConfig.getPercentage());
        request.setId(alertConfig.getId());
        return apiClient.post("/AlertConfig/UpdateAlertConfig", request);
    }
}
