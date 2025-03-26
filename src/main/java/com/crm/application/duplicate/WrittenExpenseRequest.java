package com.crm.application.duplicate;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WrittenExpenseRequest {
    private String number;
    private String title;
    private String description;
    private LocalDateTime expenseDate;
    private int status;
    private double amount;
    private String campaignId;
}
