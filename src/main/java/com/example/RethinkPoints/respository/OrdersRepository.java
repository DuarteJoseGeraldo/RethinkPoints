package com.example.RethinkPoints.respository;

import com.example.RethinkPoints.dto.enums.OrderStatus;
import com.example.RethinkPoints.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, String> {
    Optional<OrdersEntity> findById(String id);
    Optional<OrdersEntity> findByToken(String token);
    Optional<List<OrdersEntity>> findByStatus(OrderStatus status);
    Optional<List<OrdersEntity>> findAllByStatusAndCreditDateBefore(OrderStatus status, LocalDateTime date);
}
