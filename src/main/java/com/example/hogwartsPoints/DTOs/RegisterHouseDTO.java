package com.example.hogwartsPoints.DTOs;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterHouseDTO {
    @NonNull
    String name;
}
