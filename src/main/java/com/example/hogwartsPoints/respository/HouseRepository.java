package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseRepository extends JpaRepository<House, Long> {
    Optional<House> findByNameContainingIgnoreCase(String houseName);
}
