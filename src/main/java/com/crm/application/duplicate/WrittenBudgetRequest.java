package com.crm.application.duplicate;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WrittenBudgetRequest {
    private String number;
    private String title;
    private String description;
    private LocalDateTime budgetDate;
    private int status;
    private double amount;
    private String campaignId;
}
