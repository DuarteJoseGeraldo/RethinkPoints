package com.example.RethinkPoints.respository;

import com.example.RethinkPoints.entity.PartnerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartnerRepository extends JpaRepository<PartnerEntity, Long> {
    Optional<PartnerEntity> findByClientId(String clientId);
    Optional<PartnerEntity> findByCode(String partnerCode);
    Optional<PartnerEntity> findById(Long id);
    void deleteById(Long id);
}
