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
    @Column(name = "cpf")
    private String cpf;
    @Column(name = "id_campaign")
    private String idCampaign;
    @Column(name = "partner_code")
    private String partnerCode;
    @Column(name = "click_date")
    private LocalDateTime clickDate;
}