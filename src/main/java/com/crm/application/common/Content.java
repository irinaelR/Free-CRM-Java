package com.crm.application.common;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Content<T> {
    List<T> data;
}
