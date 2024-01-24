package com.example.RethinkPoints.entity;

import com.example.RethinkPoints.dto.enums.OrderStatus;
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
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @Basic(optional = false)
    @Column(name = "id", unique = true, nullable = false)
    private  String id;
    @Column(name = "user_cpf", nullable = false)
    private String userCpf;
    @Column(name = "partner_code", nullable = false)
    private String partnerCode;
    @Column(name = "token", nullable = false)
    private String token;
    @Column(name = "order_number", nullable = false)
    private String orderNumber;
    @Column(name = "order_date", nullable = false)
    private LocalDateTime orderDate;
    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany(mappedBy = "order", cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    private List<ItemEntity> items;
    @Column(name = "total")
    private float total;
    @Column(name = "change_date")
    private LocalDateTime changeDate;
    @Column(name = "points", nullable = false)
    private float points;
    @Column(name = "credit_date")
    private LocalDateTime creditDate;
}