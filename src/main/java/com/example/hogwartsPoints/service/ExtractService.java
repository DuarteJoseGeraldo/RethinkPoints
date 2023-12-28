package com.example.hogwartsPoints.service;
import com.example.hogwartsPoints.entity.ExtractEntity;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.respository.ExtractRepository;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtractService {
    private final ExtractRepository extractRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public List<ExtractEntity> getUserExtract(String accessToken) throws Exception {
        UserEntity user = jwtUtil.userTokenValidator(accessToken);
        List<ExtractEntity> extracts = extractRepo.findByUserCpf(user.getCpf());
        if (extracts.isEmpty()) throw new EntityNotFoundException("No extracts available yet");
        return extracts;
    }

    public List<ExtractEntity> getUserExtractById(String accessToken, Long userId) throws Exception {
        jwtUtil.adminValidator(accessToken);
        UserEntity user = userRepo.findById(userId).orElseThrow(()-> new EntityNotFoundException("User not found"));
        List<ExtractEntity> extracts = extractRepo.findByUserCpf(user.getCpf());
        if (extracts.isEmpty()) throw new EntityNotFoundException("No extracts available yet");
        return extracts;
    }
}
