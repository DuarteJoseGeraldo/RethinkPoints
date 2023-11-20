package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(long id);
    Optional<UserEntity> findByCpf(String cpf);
}
