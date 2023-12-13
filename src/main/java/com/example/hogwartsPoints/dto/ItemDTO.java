package com.example.hogwartsPoints.dto;


import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class ItemDTO {
    private String sku;
    private double price;
    private int quantity;
}
