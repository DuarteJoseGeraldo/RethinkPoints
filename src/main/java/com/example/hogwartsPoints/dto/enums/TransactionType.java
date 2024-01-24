package com.example.hogwartsPoints.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TransactionType {
    CREDIT("credit"),
    RESCUE("rescue");
    private final String value;
    public static TransactionType fromValue(String value) {
        for (TransactionType transactionType : TransactionType.values()) {
            if (transactionType.value.equalsIgnoreCase(value)) {
                return transactionType;
            }
        }
        throw new IllegalArgumentException("Invalid Transaction Type: " + value);
    }
}
