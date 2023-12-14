package com.example.hogwartsPoints.dto;

import com.example.hogwartsPoints.dto.enums.LoginType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDataDTO {
    private String userIdentifier;
    private LoginType loginType;
}