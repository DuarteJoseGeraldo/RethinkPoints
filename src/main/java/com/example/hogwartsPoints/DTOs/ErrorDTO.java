package com.example.hogwartsPoints.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorDTO {
    private String errorMessage;

}
