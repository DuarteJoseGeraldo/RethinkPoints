package com.example.hogwartsPoints.dto.update;

import com.example.hogwartsPoints.dto.AddressDTO;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String name;
    private String hub;
    private AddressDTO address;
}
