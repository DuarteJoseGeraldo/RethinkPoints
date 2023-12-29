package com.example.hogwartsPoints.entity;
import com.example.hogwartsPoints.dto.enums.TransactionType;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="extract")
public class ExtractEntity implements Serializable {
    private static final long serialVersionUID = -3695261560195325557L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private long id;
    @Column(name = "user_cpf", nullable = false)
    private String userCpf;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrdersEntity order;
    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "partner_code", referencedColumnName = "code")
    private PartnerEntity partner;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_code", referencedColumnName = "code")
    private ProductEntity product;
    @Column(name = "total")
    private double total;
    @Column(name = "points")
    private int points;
    @Column(name = "transaction_date")
    private LocalDateTime transactionDate;
    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_type")
    private TransactionType transactionType;
}