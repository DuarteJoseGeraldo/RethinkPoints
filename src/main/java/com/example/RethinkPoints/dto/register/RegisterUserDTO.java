package com.example.RethinkPoints.dto.register;
import com.example.RethinkPoints.dto.AddressDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterUserDTO {
    private long id;
    @NonNull
    private String cpf;
    @NonNull
    private String name;
    @NonNull
    private String hub;
    @NonNull
    private AddressDTO address;
    @NonNull
    private String password;
    private String userType;
    private Float points = 0.0F;
    private LocalDateTime lastLogin;
}
