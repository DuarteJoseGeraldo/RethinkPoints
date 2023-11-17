package com.example.hogwartsPoints.DTOs;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TokenDataDTO {
    private long id;
    private String name;
    private String house;
}
