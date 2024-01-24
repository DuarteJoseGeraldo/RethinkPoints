package com.example.RethinkPoints.dto.register;
import lombok.*;
@Data
@Builder
public class RegisterPartnerDTO {
    @NonNull
    private String name;
    @NonNull
    private String code;
    @NonNull
    private int creditDays;
}