package com.crm.application.entities.campaign;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.common.Status;
import com.crm.application.entities.salesteam.SalesTeam;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Campaign")
public class CampaignController {
    private final ApiClient apiClient;

    public CampaignController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("")
    public List<Campaign> getCampaigns() {
        return Campaign.getList(apiClient);
    }

    @GetMapping("/Status")
    public List<Status> getStatus() {
        return apiClient.getList("/Campaign/GetCampaignStatusList", Status.class);
    }


}
