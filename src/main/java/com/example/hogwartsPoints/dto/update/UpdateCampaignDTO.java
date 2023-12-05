package com.example.hogwartsPoints.dto.update;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCampaignDTO {
    private String idCampaign;
    private String description;
    private Float ourParity;
    private Float partnerParity;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
}
