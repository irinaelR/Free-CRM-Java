package com.crm.application.entities.expense;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.common.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/Expense")
public class ExpenseController {
    private final ApiClient apiClient;

    public ExpenseController(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @GetMapping("")
    public List<Expense> getAllExpenses() {
        return Expense.getExpenseList(apiClient);
    }

    @PostMapping("/Update")
    public String updateExpense(@RequestBody ExpenseUpdateRequest expense) throws JsonProcessingException {
        return apiClient.post("/Expense/UpdateExpense", expense);
    }

    @PostMapping("/Delete")
    public String deleteExpense(@RequestBody ExpenseDeleteRequest expense) throws JsonProcessingException {
        return apiClient.post("/Expense/DeleteExpense", expense);
    }

    @GetMapping("/Status")
    public List<Status> getStatus() {
        return apiClient.getList("/Expense/GetExpenseStatusList", Status.class);
    }

    @PostMapping("/SumPerMonth")
    public Map<Integer, Double> sumPerMonth(@RequestBody Integer status) throws JsonProcessingException {
        return Expense.sumExpensesByMonth(Expense.getExpenseList(apiClient), 2025, status);
    }
}
