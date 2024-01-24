package com.example.RethinkPoints.dto.register;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class RegisterDefaultCampaignDTO {
        private Float ourParity;
        private Float partnerParity;
        private String partnerCode;
}