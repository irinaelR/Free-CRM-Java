package com.crm.application.entities.salesteam;

import com.crm.application.common.ApiClient;
import com.crm.application.common.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/SalesTeam")
public class SalesTeamController {
    private final ApiClient apiClient;

    public SalesTeamController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("")
    public List<SalesTeam> getSalesTeam() {
        return SalesTeam.getList(apiClient);
    }
}
