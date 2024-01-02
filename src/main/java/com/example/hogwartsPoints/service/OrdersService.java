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

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {
    private final OrdersRepository ordersRepo;
    private final UserRepository userRepo;
    private final CampaignRepository campaignRepo;
    private final PartnerRepository partnerRepo;
    private final HotsiteRepository hotsiteRepo;
    private final JwtUtil jwtUtil;
    private final CampaignService campaignService;
    private final HotsiteService hotsiteService;

    public OrdersEntity getInfo(String accessToken,String id) throws Exception {
        jwtUtil.adminValidator(accessToken);
        return ordersRepo.findById(id).orElseThrow(() -> new EntityNotFoundException("order not found"));
    }

    public OrdersEntity createOrder(String accessToken, RegisterOrderDTO orderData) throws Exception {
        PartnerEntity requestPartner = jwtUtil.partnerTokenValidator(accessToken);
        OrdersEntity newOrder = validateOrderData(orderData, requestPartner);
        newOrder.setItems(buildItemsList(orderData.getItems(), newOrder));
        hotsiteService.orderConfirmation(orderData.getToken());
        return ordersRepo.save(newOrder);
    }

    public OrdersEntity confirmOrder(String accessToken, RegisterOrderDTO orderData) throws Exception {
        PartnerEntity partnerData = jwtUtil.partnerTokenValidator(accessToken);
        OrdersEntity pendingOrder = ordersRepo.findById(orderData.getOrderNumber() + "_" + partnerData.getCode()).orElseThrow(() -> new EntityNotFoundException("Order not found"));
        validateConfirmOrderData(orderData, pendingOrder);
        if (orderData.getStatus().equals(OrderStatus.ORDER_CANCELED)) return ordersRepo.save(pendingOrder);
        validateItemsList(orderData, pendingOrder);
        pendingOrder.setTotal(calculateTotalPrice(pendingOrder.getItems()));
        pendingOrder.setPoints(campaignService.calculatePoints(hotsiteService.hotsiteTokenValidator(orderData.getToken()).getIdCampaign(), pendingOrder.getTotal()));
        pendingOrder.setStatus(orderData.getStatus());
        pendingOrder.setCreditDate(LocalDateTime.now().plusDays(partnerData.getCreditDays()));
        return ordersRepo.save(pendingOrder);
    }

    private void validateConfirmOrderData(RegisterOrderDTO confirmationData, OrdersEntity pendingOrder) {
        if (!pendingOrder.getStatus().equals(OrderStatus.WAITING_CONFIRMATION))
            throw new IllegalArgumentException("Order status has already been changed");
        if (!Objects.equals(confirmationData.getUserCpf(), pendingOrder.getUserCpf()))
            throw new IllegalArgumentException("Order does not belong to this user");
        if (Objects.equals(confirmationData.getToken(), pendingOrder.getToken()))
            hotsiteService.hotsiteTokenValidator(confirmationData.getToken());
        else throw new IllegalArgumentException("Order does not belong to this hotsite click token");
        if (!confirmationData.getOrderDate().equals(pendingOrder.getOrderDate()))
            throw new IllegalArgumentException("Order date does not match");
        if (confirmationData.getStatus().equals(OrderStatus.ORDER_CANCELED))
            pendingOrder.setStatus(OrderStatus.ORDER_CANCELED);
        pendingOrder.setChangeDate(LocalDateTime.now());
    }

    private OrdersEntity validateOrderData(RegisterOrderDTO orderData, PartnerEntity requestPartner) {
        if (ordersRepo.findByToken(orderData.getToken()).isPresent())
            throw new EntityExistsException("Hotsite token is already linked to an order");
        if (userRepo.findByCpf(orderData.getUserCpf()).isEmpty())
            throw new EntityNotFoundException("User of order not found");
        HotsiteEntity hotsiteData = hotsiteService.hotsiteTokenValidator(orderData.getToken());
        if (!orderData.getUserCpf().equals(hotsiteData.getCpf()))
            throw new IllegalArgumentException("Hotsite token does not belong to the user of the order");
        if (!requestPartner.getCode().equals(hotsiteData.getPartnerCode()))
            throw new IllegalArgumentException("Hotsite token does not belong to the partner of the order");
        PartnerEntity partner = partnerRepo.findByCode(requestPartner.getCode()).orElseThrow(() -> new EntityNotFoundException("Partner Not Found"));
        if (partner.getStatus().equals(Status.INACTIVE)) throw new IllegalArgumentException("Partner is not active");
        CampaignEntity campaignData = campaignRepo.findByIdCampaign(hotsiteData.getIdCampaign()).orElseThrow(() -> new EntityNotFoundException("Campaign not found at validation"));
        if (orderData.getOrderDate().isAfter(campaignData.getEndAt())) {
            campaignData = campaignRepo.findByIdCampaign("DEFAULT" + hotsiteData.getPartnerCode()).orElseThrow(() -> new EntityNotFoundException("Default Campaign not found"));
            hotsiteData.setIdCampaign(campaignData.getIdCampaign());
            hotsiteRepo.save(hotsiteData);
        }
        float total = calculateTotalPrice(orderData.getItems());
        return OrdersEntity.builder()
                .id(orderData.getOrderNumber() + "_" + requestPartner.getCode())
                .userCpf(orderData.getUserCpf())
                .partnerCode(requestPartner.getCode())
                .token(orderData.getToken())
                .orderNumber(orderData.getOrderNumber())
                .orderDate(orderData.getOrderDate())
                .status(OrderStatus.WAITING_CONFIRMATION)
                .total(total)
                .points(campaignService.calculatePoints(campaignData.getIdCampaign(), total)).build();
    }

    private void validateItemsList(RegisterOrderDTO confirmationData, OrdersEntity pendingOrder) {
        if (confirmationData.getItems().size() != pendingOrder.getItems().size()) {
            throw new IllegalArgumentException("Missing or extra item in the list, send the same items of the notification");
        }

        // Criar um conjunto de SKUs a partir dos itens de confirmação
        Set<String> confirmationSkus = confirmationData.getItems().stream()
                .map(ItemDTO::getSku)
                .collect(Collectors.toSet());

        for (ItemEntity pendingItem : pendingOrder.getItems()) {
            // Verificar se a SKU do item pendente está presente nos itens de confirmação
            if (confirmationSkus.contains(pendingItem.getSku())) {
                // Encontrar o item correspondente nos itens de confirmação
                ItemDTO matchingConfirmationItem = confirmationData.getItems().stream()
                        .filter(confItem -> confItem.getSku().equals(pendingItem.getSku()))
                        .findFirst()
                        .orElseThrow(() -> new IllegalArgumentException("Unexpected error: matching item not found."));

                // Atualizar a quantidade do item pendente
                pendingItem.setQuantity(Math.max(matchingConfirmationItem.getQuantity(), 0));
            } else {
                throw new IllegalArgumentException("Received order sku that does not belong to the order being processed");
            }
        }
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

    private <T> float calculateTotalPrice(List<T> items) {
        float total = 0;
        for (T item : items) {
            if (item instanceof ItemDTO) {
                total += (float) (((ItemDTO) item).getPrice() * ((ItemDTO) item).getQuantity());
            } else if (item instanceof ItemEntity) {
                total += (float) (((ItemEntity) item).getPrice() * ((ItemEntity) item).getQuantity());
            }
        }
        return total;
    }
}