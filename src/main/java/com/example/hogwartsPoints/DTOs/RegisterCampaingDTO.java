package com.example.hogwartsPoints.DTOs;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Data
@Builder
public class RegisterCampaingDTO {
    private  long id;
    @NonNull
    private String idCampaing;
    @NonNull
    private String description;
    private Float ourParity;
    private Float partnerParity;
    @NonNull
    private LocalDateTime startAt;
    @NonNull
    private LocalDateTime endAt;
}
