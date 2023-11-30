package com.example.hogwartsPoints.dto.register;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterHouseDTO {
    @NonNull
    String name;
}
