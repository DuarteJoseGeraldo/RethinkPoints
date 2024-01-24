package com.example.RethinkPoints.respository;

import com.example.RethinkPoints.entity.CampaignEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CampaignRepository extends JpaRepository<CampaignEntity, Long> {
    Optional<CampaignEntity> findById(Long id);
    Optional<CampaignEntity> findByIdCampaign(String idCampaign);
}
