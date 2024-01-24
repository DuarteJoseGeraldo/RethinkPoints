package com.example.RethinkPoints.dto.register;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
public class RegisterCampaignDTO {
    @NonNull
    private String idCampaign;
    @NonNull
    private String description;
    @NonNull
    private Float ourParity;
    @NonNull
    private String partnerCode;
    @NonNull
    private Float partnerParity;
    @NonNull
    private LocalDateTime startAt;
    @NonNull
    private LocalDateTime endAt;
}
