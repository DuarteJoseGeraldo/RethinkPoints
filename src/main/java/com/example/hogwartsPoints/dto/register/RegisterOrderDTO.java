package com.example.hogwartsPoints.dto.register;

import com.example.hogwartsPoints.dto.ItemDTO;
import com.example.hogwartsPoints.dto.enums.OrderStatus;
import com.example.hogwartsPoints.entity.ItemEntity;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class RegisterOrderDTO {
    @NonNull
    private String userCpf;
    @NonNull
    private String token;
    @NonNull
    private String orderNumber;
    @NonNull
    private LocalDateTime orderDate;
    @NonNull
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @NonNull
    private List<ItemDTO> items;
}
