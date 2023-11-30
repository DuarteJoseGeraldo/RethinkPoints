package com.example.hogwartsPoints.dto.register;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterCampaignDTO {
    private  long id;
    @NonNull
    private String idCampaign;
    @NonNull
    private String description;
    private Float ourParity;
    private Float partnerParity;
    @NonNull
    private LocalDateTime startAt;
    @NonNull
    private LocalDateTime endAt;
}
