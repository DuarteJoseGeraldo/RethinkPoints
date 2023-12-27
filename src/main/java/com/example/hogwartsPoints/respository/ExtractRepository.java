package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.ExtractEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface ExtractRepository extends JpaRepository<ExtractEntity, Long> {
    Optional<List<ExtractEntity>> findByUserCpf(String cpf);
    Optional<ExtractEntity> findById(Long id);
}
