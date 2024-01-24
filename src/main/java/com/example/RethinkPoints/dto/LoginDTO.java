package com.example.RethinkPoints.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@Data
@Builder
public class LoginDTO {
    @NonNull
    private String cpf;
    @NonNull
    private String password;
}
