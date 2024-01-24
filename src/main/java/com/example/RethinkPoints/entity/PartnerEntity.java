package com.example.RethinkPoints.entity;

import com.example.RethinkPoints.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "partner")
public class PartnerEntity implements Serializable {
    private static final long serialVersionUID = -3695261560195325557L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_secret")
    private String clientSecret;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "credit_days")
    private int creditDays;
}
