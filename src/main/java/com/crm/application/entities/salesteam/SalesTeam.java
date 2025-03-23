package com.crm.application.entities.salesteam;

import com.crm.application.common.ApiClient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class SalesTeam {
    String id;
    String name;
    String description;
    LocalDateTime createdAtUtc;

    public static List<SalesTeam> getList(ApiClient apiClient) {
        return apiClient.getList("/SalesTeam/GetSalesTeamList", SalesTeam.class);
    }
}
