package com.example.RethinkPoints.respository;

import com.example.RethinkPoints.dto.id.ItemEntityId;
import com.example.RethinkPoints.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<ItemEntity, ItemEntityId> {

}
