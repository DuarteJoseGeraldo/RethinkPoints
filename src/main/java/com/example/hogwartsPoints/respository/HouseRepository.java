package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity, Long> {
    Optional<HouseEntity> findByNameContainingIgnoreCase(String houseName);
}
