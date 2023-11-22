package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.RegisterHouseDTO;
import com.example.hogwartsPoints.entity.HouseEntity;
import com.example.hogwartsPoints.respository.HouseRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class HouseService {
    private final HouseRepository houseRepo;
    public HouseEntity registerHouse (RegisterHouseDTO houseData){
        if(houseRepo.findByNameContainingIgnoreCase(houseData.getName()).isPresent()) throw new EntityExistsException("House Already registered");

        return houseRepo.save(HouseEntity.builder().name(houseData.getName()).build());
    }

    public HouseEntity getHouseDataByName  (String name){
        return houseRepo.findByNameContainingIgnoreCase(name).orElseThrow(()-> new EntityNotFoundException("House not Found"));
    }
}
