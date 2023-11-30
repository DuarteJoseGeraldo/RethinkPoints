package com.example.hogwartsPoints.dto.update;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private long id;
    private String cpf;
    private String name;
    private String house;
}
