package com.example.hogwartsPoints.dto.register;
import lombok.*;
@Data
@Builder
public class RegisterPartnerDTO {
    @NonNull
    private String name;
    @NonNull
    private String code;
}