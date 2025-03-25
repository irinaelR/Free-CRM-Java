package com.crm.application.entities.expense;

import com.crm.application.common.ApiClient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ExpenseUpdateRequest {
    String id;
    String title;
    String description;
    LocalDateTime expenseDate;
    String status;
    double amount;
    String campaignId;
    String updatedById;

    public String update(ApiClient apiClient) throws Exception {
        if(amount <= 0) {
            throw new Exception("Amount must be greater than 0");
        }
        return apiClient.post("/Campaign/UpdateCampaign", this);
    }
}
