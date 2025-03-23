package com.crm.application.entities.expense;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExpenseDeleteRequest {
    String id;
    String deletedById;
}
