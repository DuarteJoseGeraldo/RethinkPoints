package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<ProductEntity, Long> {
    Optional<ProductEntity> findById(Long id);
    Optional<ProductEntity> findByCode(String  code);
}
