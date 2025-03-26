package com.crm.application.duplicate;

import com.crm.application.common.ApiClient;
import com.crm.application.entities.budget.Budget;
import com.crm.application.entities.campaign.Campaign;
import com.crm.application.entities.expense.Expense;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Getter
@Setter
public class DuplicateManager {
    private MultipartFile file;

    public void read(ApiClient apiClient) throws IOException, ParseException {
        InputStream inputStream = getFile().getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        String line;
        boolean makeCampaigns = false;
        boolean makeBudgets = false;
        boolean makeExpenses = false;
        String campaignId = null;

        while ((line = reader.readLine()) != null) {
            if (line.equals("---CAMPAIGN---")) {
                makeCampaigns = true;
            }
            else if (line.equals("---BUDGET---")) {
                makeCampaigns = false;
                makeBudgets = true;
            }
            else if (line.equals("---EXPENSE---")) {
                makeBudgets = false;
                makeExpenses = true;
            }
            else {
                String[] parts = line.split(";");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
                NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.FRANCE);

                if (makeCampaigns) {
                    WrittenCampaignRequest c = new WrittenCampaignRequest();
                    c.setNumber(parts[0]);
                    c.setTitle(parts[1]);
                    c.setDescription(parts[2]);
                    c.setTargetRevenueAmount(numberFormat.parse(parts[3]).doubleValue());
                    c.setCampaignDateStart(LocalDateTime.parse(parts[4], formatter));
                    c.setCampaignDateFinish(LocalDateTime.parse(parts[5], formatter));
                    c.setStatus(Integer.valueOf(parts[6]));
                    c.setSalesTeamId(parts[7]);
                    String response = apiClient.post("/ObjectCopy/SaveCampaign", c);
                    campaignId = response;
                }

                if (makeBudgets) {
                    WrittenBudgetRequest b = new WrittenBudgetRequest();
                    b.setNumber(parts[0]);
                    b.setTitle(parts[1]);
                    b.setDescription(parts[2]);
                    b.setBudgetDate(LocalDateTime.parse(parts[3], formatter));
                    b.setStatus(Integer.valueOf(parts[4]));
                    b.setAmount(numberFormat.parse(parts[5]).doubleValue());
                    b.setCampaignId(campaignId);
                    apiClient.post("/ObjectCopy/SaveBudget", b);
                }

                if (makeExpenses) {
                    WrittenExpenseRequest e = new WrittenExpenseRequest();
                    e.setNumber(parts[0]);
                    e.setTitle(parts[1]);
                    e.setDescription(parts[2]);
                    e.setExpenseDate(LocalDateTime.parse(parts[3], formatter));
                    e.setStatus(Integer.valueOf(parts[4]));
                    e.setAmount(numberFormat.parse(parts[5]).doubleValue());
                    e.setCampaignId(campaignId);
                    apiClient.post("/ObjectCopy/SaveExpense", e);
                }
            }
        }
        reader.close();
    }
}
