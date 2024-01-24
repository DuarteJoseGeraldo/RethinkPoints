package com.example.RethinkPoints.dto.register;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class RegisterProductDTO {
    @NonNull
    private String code;
    @NonNull
    private String name;
    @NonNull
    private double price;
    @NonNull
    private int points;
}
