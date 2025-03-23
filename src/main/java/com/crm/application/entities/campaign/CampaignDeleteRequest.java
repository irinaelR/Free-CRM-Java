package com.crm.application.entities.campaign;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CampaignDeleteRequest {
    String id;
    String deletedById;
}
