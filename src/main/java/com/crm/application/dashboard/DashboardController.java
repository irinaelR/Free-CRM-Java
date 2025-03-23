package com.crm.application.dashboard;

import com.crm.application.common.ApiClient;
import com.crm.application.dashboard.dto.ExpenseSumArgsDTO;
import com.crm.application.dashboard.dto.TotalDTO;
import com.crm.application.entities.budget.Budget;
import com.crm.application.entities.campaign.Campaign;
import com.crm.application.entities.expense.Expense;
import com.crm.application.entities.salesteam.SalesTeam;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Dashboard")
public class DashboardController {
    private ApiClient apiClient;
    public DashboardController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("/Totals")
    public List<TotalDTO> getTotals() {
        List<TotalDTO> result = new ArrayList<>();

        List<Campaign> campaignList = Campaign.getList(apiClient);
        result.add(new TotalDTO("/Details/Campaign", "Total target revenue amount", Campaign.getTotalTargetRevenueAmount(campaignList)));

        List<Budget> budgets = Budget.getBudgetList(apiClient);
        result.add(new TotalDTO("/Details/Budget", "Total budgets amount", Budget.getTotalBudgetAmount(budgets)));

        List<Expense> expenses = Expense.getExpenseList(apiClient);
        result.add(new TotalDTO("/Details/Expense", "Total expenses amount", Expense.getExpenseAmount(expenses)));

        return result;
    }

    @PostMapping("/BudgetAmountsPerCampaign")
    public Map<String, Double> getBudgetAmountMap(@RequestBody Integer status) {
        List<Budget> budgetList = Budget.getBudgetList(apiClient);
        List<Campaign> campaigns = Campaign.getList(apiClient);
        return Budget.calculateBudgetAmount(budgetList, campaigns, status);
    }

    @PostMapping("/CampaignCountPerTeam")
    public Map<String, Long> getCountPerTeam(@RequestBody Integer status) {
        List<SalesTeam> salesTeams = SalesTeam.getList(apiClient);
        List<Campaign> campaigns = Campaign.getList(apiClient);
        return Campaign.countCampaignsPerTeam(campaigns, salesTeams, status);
    }

    @PostMapping("/ExpensesSumPerMonth")
    public Map<Integer, Double> sumPerMonth(@RequestBody ExpenseSumArgsDTO expenseSumArgsDTO) {
        return Expense.sumExpensesByMonth(Expense.getExpenseList(apiClient), expenseSumArgsDTO.getYear(), expenseSumArgsDTO.getStatus());
    }
}
