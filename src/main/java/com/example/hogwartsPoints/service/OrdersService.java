package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.ItemDTO;
import com.example.hogwartsPoints.dto.enums.OrderStatus;
import com.example.hogwartsPoints.dto.id.ItemEntityId;
import com.example.hogwartsPoints.dto.register.RegisterOrderDTO;
import com.example.hogwartsPoints.entity.HotsiteEntity;
import com.example.hogwartsPoints.entity.ItemEntity;
import com.example.hogwartsPoints.entity.OrdersEntity;
import com.example.hogwartsPoints.entity.PartnerEntity;
import com.example.hogwartsPoints.respository.HotsiteRepository;
import com.example.hogwartsPoints.respository.OrdersRepository;
import com.example.hogwartsPoints.respository.UserRepository;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrdersService {
    private final OrdersRepository ordersRepo;
    private final UserRepository userRepo;
    private final HotsiteRepository hotsiteRepo;
    private final JwtUtil jwtUtil;
    private final CampaignService campaignService;

    public OrdersEntity getInfo(String id) {
        return ordersRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("order not found"));
    }

    public OrdersEntity createOrder(String accessToken, RegisterOrderDTO orderData) throws Exception {
        PartnerEntity requestPartner = jwtUtil.partnerTokenValidator(accessToken);
        OrdersEntity newOrder = validateOrderData(orderData, requestPartner);
        newOrder.setItems(buildItemsList(orderData.getItems(), newOrder.getId()));
        HotsiteEntity updateHotsite = hotsiteRepo.findById(orderData.getToken()).orElseThrow(()-> new EntityNotFoundException("Hotsite Token not found - final"));
        updateHotsite.setOrderConfirmation(true);
        hotsiteRepo.save(updateHotsite);
        return ordersRepo.save(newOrder);
    }

    private OrdersEntity validateOrderData(RegisterOrderDTO orderData, PartnerEntity requestPartner) {
        if (userRepo.findByCpf(orderData.getUserCpf()).isEmpty())
            throw new EntityNotFoundException("User of order not found");
        HotsiteEntity hotsiteData = jwtUtil.hotsiteTokenValidator(orderData.getToken());
        if (!orderData.getUserCpf().equals(hotsiteData.getCpf()))
            throw new IllegalArgumentException("Hotsite token does not belong to the user of the order");
        if (!requestPartner.getCode().equals(hotsiteData.getPartnerCode()))
            throw new IllegalArgumentException("Hotsite token does not belong to the partner of the order");
        double total = calculateTotalPrice(orderData.getItems());
        return OrdersEntity.builder()
                .id(orderData.getOrderNumber() + requestPartner.getCode())
                .userCpf(orderData.getUserCpf())
                .partnerCode(requestPartner.getCode())
                .token(orderData.getToken())
                .orderNumber(orderData.getOrderNumber())
                .orderDate(orderData.getOrderDate())
                .status(OrderStatus.WAITING_CONFIRMATION)
                .total(total)
                .points(campaignService.calculatePoints(hotsiteData.getIdCampaign(),total )).build();
    }

    private List<ItemEntity> buildItemsList(List<ItemDTO> items, String orderId) {
        long i = 1;
        List<ItemEntity> itemList = new ArrayList<>();
        for (ItemDTO item : items) {
            itemList.add(
                    ItemEntity.builder()
                            .id(ItemEntityId.builder().id(i).orderId(orderId).build())
                            .sku(item.getSku())
                            .price(item.getPrice())
                            .quantity(item.getQuantity()).build());
            i++;
        }
        return itemList;
    }

    private double calculateTotalPrice(List<ItemDTO> items){
        double total = 0;
        for(ItemDTO item : items){
            total+= item.getPrice()*item.getQuantity();
        }
        return total;
    }
}
