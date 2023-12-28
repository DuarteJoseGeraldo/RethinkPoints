package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.enums.OrderStatus;
import com.example.hogwartsPoints.dto.enums.TransactionType;
import com.example.hogwartsPoints.entity.ExtractEntity;
import com.example.hogwartsPoints.entity.OrdersEntity;
import com.example.hogwartsPoints.entity.UserEntity;
import com.example.hogwartsPoints.respository.ExtractRepository;
import com.example.hogwartsPoints.respository.OrdersRepository;
import com.example.hogwartsPoints.respository.PartnerRepository;
import com.example.hogwartsPoints.respository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PointsService {
    private final ExtractRepository extractRepo;
    private final OrdersRepository ordersRepo;
    private final UserRepository userRepo;
    private final PartnerRepository partnerRepository;

    @Transactional
    public void creditPointsOfConfirmedOrders(){
        try {
            Optional<List<OrdersEntity>> confirmedOrdersOptional = ordersRepo.findAllByStatusAndCreditDateBefore(OrderStatus.ORDER_CONFIRMED, LocalDateTime.now());
            if (confirmedOrdersOptional.isPresent() && !confirmedOrdersOptional.get().isEmpty() ) {
                List<OrdersEntity> confirmedOrders = confirmedOrdersOptional.get();
                List<ExtractEntity> extracts = new ArrayList<>();
                for(OrdersEntity order : confirmedOrders ){
                    UserEntity user = userRepo.findByCpf(order.getUserCpf()).orElseThrow(() -> new EntityNotFoundException("User not found"));
                    user.setPoints((float) Math.round(user.getPoints() + order.getPoints()));
                    order.setStatus(OrderStatus.ORDER_CREDITED);
                    extracts.add(ExtractEntity.builder()
                            .userCpf(user.getCpf())
                            .order(order)
                            .partner(partnerRepository.findByCode(order.getPartnerCode()).orElseThrow(()-> new EntityNotFoundException("Partner not found")))
                            .product(null)
                            .total(order.getTotal())
                            .points(Math.round(order.getPoints()))
                            .transactionDate(LocalDateTime.now())
                            .transactionType(TransactionType.CREDIT).build());
                }
                extractRepo.saveAll(extracts);
                log.info("creditPointsOfConfirmedOrders() - 'points successfully credited'");
            } else {
                log.info("creditPointsOfConfirmedOrders() - 'No order to credit found'");
            }
        }catch (Exception e){
            log.error("creditPointsOfConfirmedOrders() - 'Erro ao creditar pontos': {}", e.getMessage());
        }
    }
}
