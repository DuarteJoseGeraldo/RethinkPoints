package com.example.hogwartsPoints.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class RedemptionDTO {
    @NonNull
    private String  productCode;
    @NonNull
    private String userCpf;
}
