package com.crm.application.entities.campaign;

import com.crm.application.common.ApiClient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CampaignUpdateRequest {
    String id;
    String title;
    String description;
    double targetRevenueAmount;
    LocalDateTime campaignDateStart;
    LocalDateTime campaignDateFinish;
    String salesTeamId;
    String status;
    String updatedById;

    public String update(ApiClient apiClient) throws Exception {
        if(targetRevenueAmount <= 0) {
            throw new Exception("Target revenue amount must be greater than 0");
        }
        return apiClient.post("/Campaign/UpdateCampaign", this);
    }
}
