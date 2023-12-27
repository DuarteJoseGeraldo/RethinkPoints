package com.example.hogwartsPoints.dto.update;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateProductDTO {
    private String code;
    private String name;
    private double price;
    private int points;
}
