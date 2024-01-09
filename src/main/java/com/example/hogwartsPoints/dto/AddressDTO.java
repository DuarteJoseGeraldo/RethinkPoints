package com.example.hogwartsPoints.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDTO {
    private String street;
    private Integer number;
    private String neighborhood;
    private String complement;
    private String city;
    private String state;
    private String zipCode;
}
