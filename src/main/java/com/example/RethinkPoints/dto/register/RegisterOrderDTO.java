package com.example.RethinkPoints.dto.register;

import com.example.RethinkPoints.dto.ItemDTO;
import com.example.RethinkPoints.dto.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
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
    private List<ItemDTO> items;
    private OrderStatus status;
}