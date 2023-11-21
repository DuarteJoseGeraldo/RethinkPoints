package com.example.hogwartsPoints.dto;

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
    private String password;
    private String userType;
    private Float points = 0.0F;
    private LocalDateTime lastLogin;
}
