package com.example.RethinkPoints.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String errorMessage;

}
