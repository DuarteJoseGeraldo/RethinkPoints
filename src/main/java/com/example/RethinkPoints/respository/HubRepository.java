package com.example.RethinkPoints.respository;

import com.example.RethinkPoints.entity.HubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HubRepository extends JpaRepository<HubEntity, Long> {
    Optional<HubEntity> findByNameContainingIgnoreCase(String hubName);
}
