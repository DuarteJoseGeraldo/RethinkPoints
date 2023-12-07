package com.example.hogwartsPoints.service;

import com.example.hogwartsPoints.dto.ItemDTO;
import com.example.hogwartsPoints.dto.enums.OrderStatus;
import com.example.hogwartsPoints.dto.enums.Status;
import com.example.hogwartsPoints.dto.id.ItemEntityId;
import com.example.hogwartsPoints.dto.register.RegisterOrderDTO;
import com.example.hogwartsPoints.entity.*;
import com.example.hogwartsPoints.respository.*;
import com.example.hogwartsPoints.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {
    private final OrdersRepository ordersRepo;
    private final UserRepository userRepo;
    private final CampaignRepository campaignRepo;
    private final PartnerRepository partnerRepo;
    private final JwtUtil jwtUtil;
    private final CampaignService campaignService;
    private final HotsiteService hotsiteService;

    public OrdersEntity getInfo(String id) {
        return ordersRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("order not found"));
    }

    public OrdersEntity createOrder(String accessToken, RegisterOrderDTO orderData) throws Exception {
        PartnerEntity requestPartner = jwtUtil.partnerTokenValidator(accessToken);
        OrdersEntity newOrder = validateOrderData(orderData, requestPartner);
        newOrder.setItems(buildItemsList(orderData.getItems(), newOrder));
        hotsiteService.orderConfirmation(orderData.getToken());
        return ordersRepo.save(newOrder);
    }

    private OrdersEntity validateOrderData(RegisterOrderDTO orderData, PartnerEntity requestPartner) {
        if (userRepo.findByCpf(orderData.getUserCpf()).isEmpty())
            throw new EntityNotFoundException("User of order not found");
        HotsiteEntity hotsiteData = hotsiteService.hotsiteTokenValidator(orderData.getToken());
        if (!orderData.getUserCpf().equals(hotsiteData.getCpf()))
            throw new IllegalArgumentException("Hotsite token does not belong to the user of the order");
        if (!requestPartner.getCode().equals(hotsiteData.getPartnerCode()))
            throw new IllegalArgumentException("Hotsite token does not belong to the partner of the order");
        PartnerEntity partner = partnerRepo.findByCode(requestPartner.getCode()).orElseThrow(() -> new EntityNotFoundException("Partner Not Found"));
        if(partner.getStatus().equals(Status.INACTIVE)) throw new IllegalArgumentException("Partner is not active");
        CampaignEntity campaignData = campaignRepo.findByIdCampaign(hotsiteData.getIdCampaign()).orElseThrow(() -> new EntityNotFoundException("Campaign not found at validation"));
        if (orderData.getOrderDate().isAfter(campaignData.getEndAt()))
            campaignData = campaignRepo.findByIdCampaign("DEFAULT" + hotsiteData.getPartnerCode()).orElseThrow(() -> new EntityNotFoundException("Default Campaign not found"));
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
                .points(campaignService.calculatePoints(campaignData.getIdCampaign(), total)).build();
    }

    private List<ItemEntity> buildItemsList(List<ItemDTO> items, OrdersEntity order) {
        long i = 1;
        List<ItemEntity> itemList = new ArrayList<>();
        for (ItemDTO item : items) {
            itemList.add(
                    ItemEntity.builder()
                            .id(ItemEntityId.builder().id(i).orderId(order.getId()).build())
                            .order(order)
                            .sku(item.getSku())
                            .price(item.getPrice())
                            .quantity(item.getQuantity()).build());
            i++;
        }
        return itemList;
    }

    private double calculateTotalPrice(List<ItemDTO> items) {
        double total = 0;
        for (ItemDTO item : items) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }
}
