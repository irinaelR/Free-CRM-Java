package com.crm.application.entities.campaign;

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
}
