package com.example.hogwartsPoints.entity;
import com.example.hogwartsPoints.dto.enums.TransactionType;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;
@Data
@Entity
@Table(name="extract")
public class ExtractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    private long id;
    @Column(name = "user_cpf", nullable = false)
    private String userCpf;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private OrdersEntity order;
    @OneToOne(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
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