package com.example.RethinkPoints.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ItemDTO {
    private String sku;
    private double price;
    private int quantity;
}
