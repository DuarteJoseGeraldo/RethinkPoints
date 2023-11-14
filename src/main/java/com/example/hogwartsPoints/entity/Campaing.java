package com.example.hogwartsPoints.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "campaing")
public class Campaing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  long id;

    @Column(name = "id_campaing")
    private String idCampaing;

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
