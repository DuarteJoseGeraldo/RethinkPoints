package com.example.hogwartsPoints.dto.register;

import com.example.hogwartsPoints.dto.ItemDTO;
import com.example.hogwartsPoints.dto.enums.OrderStatus;
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
    @NonNull
    private LocalDateTime creditDate;
}
