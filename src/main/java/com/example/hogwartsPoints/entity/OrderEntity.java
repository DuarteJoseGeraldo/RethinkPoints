package com.example.hogwartsPoints.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order")
public class OrderEntity {
    @Id
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  String id;
    @Column(name = "user_cpf", nullable = false)
    private String userCpf;
    @Column(name = "partner_code", nullable = false)
    private String partner_code;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "order_number", nullable = false)
    private String orderNumber;
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
    @Column(name = "status", nullable = false)
    private String status;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemEntity> items;
    @Column(name = "total")
    private double total;
    @Column(name = "change_date")
    private LocalDateTime changeDate;
    @Column(name = "points", nullable = false)
    private float points;
}
