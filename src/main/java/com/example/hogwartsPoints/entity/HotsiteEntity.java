package com.example.hogwartsPoints.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hotsite")
public class HotsiteEntity {
    @Id
    @Column(name = "token", unique = true)
    private String token;
    @Column(name = "cpf", nullable = false)
    private String cpf;
    @Column(name = "id_campaign", nullable = false)
    private String idCampaign;
    @Column(name = "partner_code", nullable = false)
    private String partnerCode;
    @Column(name = "click_date", nullable = false)
    private LocalDateTime clickDate;
    @Column(name = "order_confirmation")
    private boolean orderConfirmation = false;
}