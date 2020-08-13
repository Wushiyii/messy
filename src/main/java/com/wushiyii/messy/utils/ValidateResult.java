package com.wushiyii.messy.utils;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ValidateResult {

    private Boolean isValid = true;
    private List<String> messages;

    public boolean isValid() {
        return isValid;
    }
}