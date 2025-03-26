package com.crm.application.entities.budget;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.common.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PostMapping("/Delete")
    public String deleteBudget(@RequestBody BudgetDeleteRequest request) throws JsonProcessingException {
        return apiClient.post("/Budget/DeleteBudget", request);
    }

    @PostMapping("/Update")
    public ResponseEntity<String> updateBudget(@RequestBody BudgetUpdateRequest request) throws Exception {
        try {
            return ResponseEntity.ok(request.update(apiClient));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
