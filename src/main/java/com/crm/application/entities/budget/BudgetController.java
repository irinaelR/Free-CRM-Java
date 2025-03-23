package com.crm.application.entities.budget;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.campaign.Campaign;
import com.crm.application.entities.common.Status;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Budget")
public class BudgetController {
    private ApiClient apiClient;
    public BudgetController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("")
    public List<Budget> getBudget() {
        return Budget.getBudgetList(apiClient);
    }

    @GetMapping("/Status")
    public List<Status> getStatus() {
        return apiClient.getList("/Budget/GetBudgetStatusList", Status.class);
    }


}
