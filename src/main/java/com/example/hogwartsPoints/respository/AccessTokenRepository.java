package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.AccessTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface AccessTokenRepository extends JpaRepository<AccessTokenEntity, String> {
    Optional<AccessTokenEntity> findByUserIdentifier(String identifier);

    void deleteByUserIdentifier(String identifier);
}
