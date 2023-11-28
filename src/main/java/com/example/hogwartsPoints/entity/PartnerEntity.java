package com.example.hogwartsPoints.entity;

import com.example.hogwartsPoints.dto.ourEnum.Status;
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
@Table(name = "partner")
public class PartnerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code", unique = true)
    private String code;
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
    @Column(name = "client_id")
    private String clientId;
    @Column(name = "client_key")
    private String clientKey;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
