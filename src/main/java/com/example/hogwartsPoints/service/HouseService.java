package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.MessagesDTO;
import com.example.hogwartsPoints.dto.register.RegisterHouseDTO;
import com.example.hogwartsPoints.dto.update.UpdateHouseDTO;
import com.example.hogwartsPoints.entity.HouseEntity;
import com.example.hogwartsPoints.respository.HouseRepository;
import static com.example.hogwartsPoints.utils.AppUtils.copyNonNullProperties;

import com.example.hogwartsPoints.utils.JwtUtil;
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
    private final JwtUtil jwtUtil;

    public HouseEntity registerHouse(String accessToken, RegisterHouseDTO houseData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        if (houseRepo.findByNameContainingIgnoreCase(houseData.getName()).isPresent())
            throw new EntityExistsException("House Already registered");
        return houseRepo.save(HouseEntity.builder().name(houseData.getName()).build());
    }

    public HouseEntity getHouseDataByName(String accessToken,String name) throws Exception {
        jwtUtil.userTokenValidator(accessToken);
        return houseRepo.findByNameContainingIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException("House not Found"));
    }

    public HouseEntity updateHouseData(String accessToken,Long houseId, UpdateHouseDTO houseNewData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        houseNewData.setId(houseId);
        HouseEntity houseData = houseRepo.findById(houseNewData.getId()).orElseThrow(() -> new EntityNotFoundException("House not Found"));
        copyNonNullProperties(houseNewData, houseData);
        log.info("updateHouseData() - 'Updated updated': {}", houseData);
        return houseRepo.save(houseData);
    }

    public MessagesDTO deleteHouse(String accessToken, Long id) throws Exception {
        jwtUtil.adminValidator(accessToken);
        houseRepo.deleteById(id);
        return MessagesDTO.builder().message("House successfully deleted").build();
    }
}
