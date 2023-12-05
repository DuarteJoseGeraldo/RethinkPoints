package com.example.hogwartsPoints.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserType {
    STANDARD("standard"),
    ADMIN("admin");
    private final String value;

    public static UserType fromValue(String value) {
        for (UserType userType : UserType.values()) {
            if (userType.value.equalsIgnoreCase(value)) {
                return userType;
            }
        }
        throw new IllegalArgumentException("Invalid Status: " + value);
    }
}
