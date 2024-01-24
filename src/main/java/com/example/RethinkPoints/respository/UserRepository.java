package com.example.RethinkPoints.respository;

import com.example.RethinkPoints.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findById(long id);
    Optional<UserEntity> findByCpf(String cpf);
}
