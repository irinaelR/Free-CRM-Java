package com.crm.application.duplicate;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class WrittenCampaignRequest {
    private String number;
    private String title;
    private String description;
    private double targetRevenueAmount;
    private LocalDateTime campaignDateStart;
    private LocalDateTime campaignDateFinish;
    private int status;
    private String salesTeamId;
}
