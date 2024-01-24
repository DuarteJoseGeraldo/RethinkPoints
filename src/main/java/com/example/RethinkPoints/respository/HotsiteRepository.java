package com.example.RethinkPoints.respository;

import com.example.RethinkPoints.entity.HotsiteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HotsiteRepository extends JpaRepository<HotsiteEntity, String> {

}
