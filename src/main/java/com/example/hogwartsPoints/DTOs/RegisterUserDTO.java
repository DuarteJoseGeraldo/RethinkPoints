package com.example.hogwartsPoints.DTOs;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.time.LocalDateTime;

@Data
@Builder
public class RegisterUserDTO {
    private long id;
    @NonNull
    private String cpf;
    @NonNull
    private String name;
    @NonNull
    private String house;
    @NonNull
    private String password;
    private String userType;
    private Float points = 0.0F;
    private LocalDateTime lastLogin;
}
