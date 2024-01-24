package com.example.RethinkPoints.controller;
import com.example.RethinkPoints.dto.register.RegisterOrderDTO;
import com.example.RethinkPoints.entity.OrdersEntity;
import com.example.RethinkPoints.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import static com.example.RethinkPoints.utils.AppUtils.getRequestToken;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;

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