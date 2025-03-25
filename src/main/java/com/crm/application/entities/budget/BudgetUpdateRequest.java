package com.crm.application.entities.budget;

import com.crm.application.common.ApiClient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BudgetUpdateRequest {
    String id;
    String title;
    String description;
    LocalDateTime budgetDate;
    String status;
    double amount;
    String campaignId;
    String updatedById;

    public String update(ApiClient apiClient) throws Exception {
        if(this.amount <= 0) {
            throw new Exception("Amount must be greater than 0");
        }
        return apiClient.post("/Budget/UpdateBudget", this);
    }
}
