package com.example.hogwartsPoints.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterHouseDTO {
    @NonNull
    String name;
}
