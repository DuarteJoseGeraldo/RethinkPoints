package com.example.hogwartsPoints.dto.ourEnum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum LoginType {
    COMMON_AUTH("common_auth"),
    CLIENT_CREDENTIALS("client_credentials");
    private final String value;
}
