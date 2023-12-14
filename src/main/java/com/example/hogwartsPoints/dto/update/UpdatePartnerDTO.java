package com.example.hogwartsPoints.dto.update;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePartnerDTO {
    private String name;
    private String code;
}
