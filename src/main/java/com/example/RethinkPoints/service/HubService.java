package com.example.RethinkPoints.service;

import com.example.RethinkPoints.dto.MessagesDTO;
import com.example.RethinkPoints.dto.register.RegisterHubDTO;
import com.example.RethinkPoints.dto.update.UpdateHubDTO;
import com.example.RethinkPoints.entity.HubEntity;
import com.example.RethinkPoints.respository.HubRepository;
import static com.example.RethinkPoints.utils.AppUtils.copyNonNullProperties;

import com.example.RethinkPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
@Slf4j
@Service
@RequiredArgsConstructor
public class HubService {
    private final HubRepository hubRepo;
    private final JwtUtil jwtUtil;

    public HubEntity registerHub(String accessToken, RegisterHubDTO hubData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        if (hubRepo.findByNameContainingIgnoreCase(hubData.getName()).isPresent())
            throw new EntityExistsException("Hub Already registered");
        return hubRepo.save(HubEntity.builder().name(hubData.getName()).build());
    }

    public HubEntity getHubDataByName(String accessToken, String name) throws Exception {
        jwtUtil.userTokenValidator(accessToken);
        return hubRepo.findByNameContainingIgnoreCase(name).orElseThrow(() -> new EntityNotFoundException("Hub not Found"));
    }

    public HubEntity updateHubData(String accessToken, Long hubId, UpdateHubDTO hubNewData) throws Exception {
        jwtUtil.adminValidator(accessToken);
        hubNewData.setId(hubId);
        HubEntity hubData = hubRepo.findById(hubNewData.getId()).orElseThrow(() -> new EntityNotFoundException("Hub not Found"));
        copyNonNullProperties(hubNewData, hubData);
        log.info("updateHubData() - 'Updated updated': {}", hubData);
        return hubRepo.save(hubData);
    }

    public MessagesDTO deleteHub(String accessToken, Long id) throws Exception {
        jwtUtil.adminValidator(accessToken);
        hubRepo.deleteById(id);
        return MessagesDTO.builder().message("Hub successfully deleted").build();
    }
}
