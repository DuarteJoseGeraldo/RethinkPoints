package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.RegisterHouseDTO;
import com.example.hogwartsPoints.dto.UpdateHouseDTO;
import com.example.hogwartsPoints.entity.HouseEntity;
import com.example.hogwartsPoints.respository.HouseRepository;
import static com.example.hogwartsPoints.utils.AppUtils.copyNonNullProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
@Slf4j
@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepo;

    public HouseEntity registerHouse(RegisterHouseDTO houseData) {
        if (houseRepo.findByNameContainingIgnoreCase(houseData.getName()).isPresent())
            throw new EntityExistsException("House Already registered");
        return houseRepo.save(HouseEntity.builder().name(houseData.getName()).build());
    }

    public HouseEntity getHouseDataByName(String name) {
        return houseRepo.findByNameContainingIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException("House not Found"));
    }

    public HouseEntity updateHouseData(UpdateHouseDTO houseNewData) {
        HouseEntity houseData = houseRepo.findById(houseNewData.getId()).orElseThrow(() -> new EntityNotFoundException("House not Found"));
        copyNonNullProperties(houseNewData, houseData);
        log.info("updateHouseData() - 'House atualizada': {}", houseData);
        return houseRepo.save(houseData);
    }

    public MessagesDTO deleteHouse(Long id) {
        houseRepo.deleteById(id);
        return MessagesDTO.builder().message("House successfully deleted").build();
    }
}
