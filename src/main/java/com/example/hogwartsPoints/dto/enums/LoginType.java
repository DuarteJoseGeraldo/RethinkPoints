package com.example.hogwartsPoints.dto.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginType {
    COMMON_AUTH("common_auth"),
    CLIENT_CREDENTIALS("client_credentials");
    private final String value;

    public static LoginType fromValue(String value) {
        for (LoginType loginType : LoginType.values()) {
            if (loginType.value.equalsIgnoreCase(value)) {
                return loginType;
            }
        }
        throw new IllegalArgumentException("Invalid Login Type: " + value);
    }
}
