package com.example.RethinkPoints.dto;

import com.example.RethinkPoints.dto.enums.LoginType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDataDTO {
    private String userIdentifier;
    private LoginType loginType;
}