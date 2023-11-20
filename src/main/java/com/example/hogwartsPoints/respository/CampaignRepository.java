package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    Optional<CampaignEntity> findById(long id);
    Optional<CampaignEntity> findByIdCampaignIgnoreCase(String idCampaign);
}
