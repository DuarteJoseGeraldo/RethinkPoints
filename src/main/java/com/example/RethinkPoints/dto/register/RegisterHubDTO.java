package com.example.RethinkPoints.dto.register;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterHubDTO {
    @NonNull
    String name;
}
