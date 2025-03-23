package com.crm.application.entities.expense;

import com.crm.application.common.ApiClient;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@Setter
public class Expense {
    private String id;
    private String number;
    private String title;
    private String description;
    private LocalDateTime expenseDate;
    public int status;
    public String statusName;
    public double amount;
    public String campaignId;
    public String campaignName;
    private LocalDateTime createdAtUtc;
    public String updatedById;

    @Override
    public String toString() {
        return "Expense{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", expenseDate=" + expenseDate +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", campaignId='" + campaignId + '\'' +
                ", updatedById='" + updatedById + '\'' +
                '}';
    }

    public static List<Expense> getExpenseList(ApiClient apiClient) {
        return apiClient.getList("/Expense/GetExpenseList", Expense.class);
    }

    public static double getExpenseAmount(List<Expense> expenseList) {
        double amount = 0;
        for (Expense expense : expenseList) {
            amount += expense.getAmount();
        }
        return amount;
    }

    public static Map<Integer, Double> sumExpensesByMonth(List<Expense> expenses, int year, Integer status) {
        Map<Integer, Double> monthlySum = new HashMap<>();
        for (int i = 1; i <= 12; i++) {
            monthlySum.put(i, 0.0);
        }

        List<Expense> filteredExpenses = expenses.stream()
                .filter(expense -> expense.getExpenseDate().getYear() == year)
                .filter(expense -> status == null || status == -1 || expense.status == status)
                .toList();

        for (Expense expense : filteredExpenses) {
            int month = expense.getExpenseDate().getMonthValue();
            double currentSum = monthlySum.get(month);
            monthlySum.put(month, currentSum + expense.amount);
        }

        return monthlySum;
    }
}
