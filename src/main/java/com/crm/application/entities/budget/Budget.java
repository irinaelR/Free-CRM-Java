package com.crm.application.entities.budget;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.campaign.Campaign;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

@Getter
@Setter
public class Budget {
    String id;
    String number;
    String title;
    String description;
    LocalDateTime budgetDate;
    int status;
    String statusName;
    double amount;
    String campaignId;
    String campaignName;
    LocalDateTime createdAtUtc;

    public static List<Budget> getBudgetList(ApiClient apiClient) {
        return apiClient.getList("/Budget/GetBudgetList", Budget.class);
    }

    public static double getTotalBudgetAmount(List<Budget> budgetList) {
        double totalBudgetAmount = 0;
        for (Budget budget : budgetList) {
            totalBudgetAmount += budget.getAmount();
        }
        return totalBudgetAmount;
    }

    public static double getAmountPerCampaign(List<Budget> budgetList, String campaignId, Integer status) {
        Predicate<Budget> predicate = budget -> budget.getCampaignId().equals(campaignId);
        if(status != null && status != -1) {
            predicate = predicate.and(budget -> budget.getStatus() == status);
        }

        return budgetList.stream().filter(predicate).mapToDouble(Budget::getAmount).sum();
    }

    public static Map<String, Double> calculateBudgetAmount(List<Budget> budgetList, List<Campaign> campaignList, Integer status) {
        Map<String, Double> budgetAmountMap = new HashMap<>();

        for (Campaign campaign : campaignList) {
            budgetAmountMap.put(campaign.getTitle(), getAmountPerCampaign(budgetList, campaign.getId(), status));
        }

        return budgetAmountMap;
    }
}
