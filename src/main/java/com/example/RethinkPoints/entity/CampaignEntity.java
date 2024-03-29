package com.example.RethinkPoints.entity;

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
@Table(name = "campaign")
public class CampaignEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  long id;
    @Column(name = "id_campaign", unique = true)
    private String idCampaign;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_id", referencedColumnName = "id")
    private PartnerEntity partner;
    @Column(name = "description")
    private String description;
    @Column(name = "our_parity")
    private Float ourParity;
    @Column(name = "partner_parity")
    private Float partnerParity;
    @Column(name = "start_at")
    private LocalDateTime startAt;
    @Column(name = "end_at")
    private LocalDateTime endAt;
}