package com.eatda.domain.order.controller;

import com.eatda.domain.order.dto.OrderRequestDTO;
import com.eatda.domain.order.dto.OrderResponseDTO;
import com.eatda.domain.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO addedOrder = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(addedOrder);
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrderList(@PathVariable Long restaurantId) {
        List<OrderResponseDTO> orderList = orderService.getOrderListByRestaurantId(restaurantId);
        return ResponseEntity.ok(orderList);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderByOrderId(@PathVariable Long orderId) {
        OrderResponseDTO addedOrder = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok(addedOrder);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByChildId(@PathVariable Long childId) {
        List<OrderResponseDTO> orderResponseDTOs = orderService.getOrdersByChildId(childId);
        return ResponseEntity.ok(orderResponseDTOs);
    }

    @GetMapping("/restaurant/president/{presidentId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrderListByPresidentId(@PathVariable Long presidentId) {
        List<OrderResponseDTO> orderList = orderService.getOrderListByPresidentId(presidentId);
        return ResponseEntity.ok(orderList);
    }
}
