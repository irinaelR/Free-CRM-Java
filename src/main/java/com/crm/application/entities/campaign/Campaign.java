package com.crm.application.entities.campaign;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.budget.Budget;
import com.crm.application.entities.budget.BudgetDeleteRequest;
import com.crm.application.entities.common.Status;
import com.crm.application.entities.expense.Expense;
import com.crm.application.entities.expense.ExpenseDeleteRequest;
import com.crm.application.entities.salesteam.SalesTeam;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Getter
@Setter
public class Campaign {
    String id;
    String number;
    String title;
    String description;
    double targetRevenueAmount;
    LocalDateTime campaignDateStart;
    LocalDateTime campaignDateFinish;
    int status;
    String statusName;
    String salesTeamId;
    String salesTeamName;
    LocalDateTime createdAtUtc;

    public static List<Campaign> getList(ApiClient client) {
        return client.getList("/Campaign/GetCampaignList", Campaign.class);
    }

    public static double getTotalTargetRevenueAmount(List<Campaign> campaigns) {
        double totalTargetRevenueAmount = 0;

        for (Campaign c : campaigns) {
            totalTargetRevenueAmount += c.getTargetRevenueAmount();
        }

        return totalTargetRevenueAmount;
    }

    public static long getAmountPerTeam(List<Campaign> campaigns, String salesTeamId, Integer status) {
        Predicate<Campaign> predicate = campaign -> campaign.getSalesTeamId().equals(salesTeamId);
        if(status != null && status != -1) {
            predicate = predicate.and(campaign -> campaign.getStatus() == status);
        }

        return campaigns.stream().filter(predicate).count();
    }

    public static Map<String, Long> countCampaignsPerTeam(List<Campaign> campaigns, List<SalesTeam> salesTeams, Integer status) {
        Map<String, Long> campaignsPerTeam = new HashMap<>();

        for (SalesTeam s : salesTeams) {
            campaignsPerTeam.put(s.getName(), getAmountPerTeam(campaigns, s.getId(), status));
        }

        return campaignsPerTeam;
    }

    public static String deleteCampaign(CampaignDeleteRequest request, ApiClient apiClient) throws JsonProcessingException {
        List<Budget> budgets = Budget.getBudgetList(apiClient);
        budgets = budgets.stream().filter(b -> b.getCampaignId().equals(request.getId())).toList();

        List<Expense> expenses = Expense.getExpenseList(apiClient);
        expenses = expenses.stream().filter(e->e.getCampaignId().equals(request.getId())).toList();

        String response = apiClient.post("/Campaign/DeleteCampaign", request);

        for (Expense e : expenses) {
            ExpenseDeleteRequest expDeleteRequest = new ExpenseDeleteRequest();
            expDeleteRequest.setId(e.getId());
            expDeleteRequest.setDeletedById(null);
            apiClient.post("/Expense/DeleteExpense", expDeleteRequest);
        }

        for (Budget budget : budgets) {
            BudgetDeleteRequest budgetDeleteRequest = new BudgetDeleteRequest();
            budgetDeleteRequest.setId(budget.getId());
            budgetDeleteRequest.setDeletedById(null);
            apiClient.post("/Budget/DeleteBudget", budgetDeleteRequest);
        }

        return response;
    }
}
