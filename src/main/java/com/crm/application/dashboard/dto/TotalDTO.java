package com.crm.application.dashboard.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TotalDTO {
    String redirect;
    String displayTitle;
    Number value;

    public TotalDTO(String redirect, String displayTitle, Number value) {
        this.redirect = redirect;
        this.displayTitle = displayTitle;
        this.value = value;
    }

    public TotalDTO() {
    }
}
