package com.example.hogwartsPoints.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ItemDTO {
    @NonNull
    private String sku;
    @NonNull
    private double price;
    @NonNull
    private int quantity;
}
