package com.crm.application.entities.campaign;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.common.Status;
import com.crm.application.entities.salesteam.SalesTeam;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/Update")
    public ResponseEntity<String> update(@RequestBody CampaignUpdateRequest request) throws Exception {
        try {
            return ResponseEntity.ok(request.update(apiClient));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/Delete")
    public String delete(@RequestBody CampaignDeleteRequest request) throws JsonProcessingException {
        return Campaign.deleteCampaign(request, apiClient);
    }
}
