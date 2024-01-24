package com.example.RethinkPoints.entity;

import com.example.RethinkPoints.dto.id.ItemEntityId;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class ItemEntity {

    @EmbeddedId
    private ItemEntityId id;
    @ManyToOne
    @MapsId("orderId") // Mapeia a coluna orderId da chave composta
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    @JsonIgnore // Evita a serialização infinita
    private OrdersEntity order;
    @Column(name = "sku", unique = true, nullable = false)
    private String sku;
    @Column(name = "price", nullable = false)
    private double price;
    @Column(name = "quantity", nullable = false)
    private int quantity;
}