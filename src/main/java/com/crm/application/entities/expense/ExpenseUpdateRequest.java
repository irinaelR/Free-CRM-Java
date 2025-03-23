package com.crm.application.entities.expense;

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

}
