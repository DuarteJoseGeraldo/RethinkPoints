package com.example.hogwartsPoints.respository;

import com.example.hogwartsPoints.dto.id.ItemEntityId;
import com.example.hogwartsPoints.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<ItemEntity, ItemEntityId> {

}
