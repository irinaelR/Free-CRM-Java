package com.crm.application.common;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponse<T> {
    int code;
    String message;
    Content<T> content;
}
