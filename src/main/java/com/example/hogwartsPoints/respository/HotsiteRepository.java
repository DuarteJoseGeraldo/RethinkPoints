package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.entity.HotsiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotsiteRepository extends JpaRepository<HotsiteEntity, String> {

}
