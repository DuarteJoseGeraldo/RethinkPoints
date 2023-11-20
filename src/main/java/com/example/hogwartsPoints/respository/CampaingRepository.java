package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.CampaingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaingRepository extends JpaRepository<CampaingEntity, Long> {
    Optional<CampaingEntity> findById(long id);
    Optional<CampaingEntity> findByIdCampaingIgnoreCase(String idCampaing);
}
