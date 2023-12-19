package com.example.hogwartsPoints.controller;
import com.example.hogwartsPoints.dto.register.RegisterOrderDTO;
import com.example.hogwartsPoints.entity.OrdersEntity;
import com.example.hogwartsPoints.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import static com.example.hogwartsPoints.utils.AppUtils.getRequestToken;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.nio.file.AccessDeniedException;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrdersController {
    private final OrdersService ordersService;

    @GetMapping()
    public ResponseEntity<?> getInfo(@RequestParam String id, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(ordersService.getInfo(getRequestToken(request) ,id));
    }

    @PostMapping(value = "/notification")
    public ResponseEntity<OrdersEntity> notifyOrder(@RequestBody RegisterOrderDTO order, HttpServletRequest request) throws Exception {
        return ResponseEntity.created(URI.create("/order/notification")).body(ordersService.createOrder(getRequestToken(request), order));
    }

    @PatchMapping(value = "/confirmation")
    public ResponseEntity<OrdersEntity> confirmOrder(@RequestBody RegisterOrderDTO order, HttpServletRequest request) throws Exception {
        return ResponseEntity.ok(ordersService.confirmOrder(getRequestToken(request), order));
    }
}