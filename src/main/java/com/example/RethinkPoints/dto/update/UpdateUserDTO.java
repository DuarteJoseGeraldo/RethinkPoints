package com.example.RethinkPoints.dto.update;

import com.example.RethinkPoints.dto.AddressDTO;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserDTO {
    private String name;
    private String hub;
    private AddressDTO address;
}
