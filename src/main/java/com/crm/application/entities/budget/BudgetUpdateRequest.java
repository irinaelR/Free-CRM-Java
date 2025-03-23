package com.crm.application.entities.budget;

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
}
