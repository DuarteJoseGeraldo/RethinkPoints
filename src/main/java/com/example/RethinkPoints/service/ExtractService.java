package com.example.RethinkPoints.service;

import com.example.RethinkPoints.entity.ExtractEntity;
import com.example.RethinkPoints.entity.UserEntity;
import com.example.RethinkPoints.respository.ExtractRepository;
import com.example.RethinkPoints.respository.UserRepository;
import com.example.RethinkPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ExtractService {
    private final ExtractRepository extractRepo;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public List<ExtractEntity> getUserExtract(String accessToken) throws Exception {
        UserEntity user = jwtUtil.userTokenValidator(accessToken);
        return getExtractByUser(user);
    }

    public List<ExtractEntity> getUserExtractById(String accessToken, Long userId) throws Exception {
        jwtUtil.adminValidator(accessToken);
        UserEntity user = userRepo.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        return getExtractByUser(user);
    }

    private List<ExtractEntity> getExtractByUser(UserEntity user) {
        List<ExtractEntity> extracts = extractRepo.findByUserCpf(user.getCpf());
        if (extracts.isEmpty()) throw new EntityNotFoundException("No extracts available yet");
        for (ExtractEntity extract : extracts) {
            if(Objects.nonNull(extract.getPartner())){
                extract.getPartner().setClientId(null);
                extract.getPartner().setClientSecret(null);
            }
        }
        return extracts;
    }
}
