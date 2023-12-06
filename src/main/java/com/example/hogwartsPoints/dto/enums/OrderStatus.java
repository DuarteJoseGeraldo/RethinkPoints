package com.example.hogwartsPoints.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OrderStatus {
    WAITING_CONFIRMATION("waiting_confirmation");
    private final String value;

    public static OrderStatus fromValue(String value) {
        for (OrderStatus orderStatus : OrderStatus.values()) {
            if (orderStatus.value.equalsIgnoreCase(value)) {
                return orderStatus;
            }
        }
        throw new IllegalArgumentException("Invalid Order Status: " + value);
    }
}
