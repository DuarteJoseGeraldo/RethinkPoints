package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.OrdersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersEntity, String> {
    Optional<OrdersEntity> findById(String id);
    Optional<OrdersEntity> findByToken(String token);
}
