package com.crm.application.entities.budget;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BudgetDeleteRequest {
    String id;
    String deletedById;
}
