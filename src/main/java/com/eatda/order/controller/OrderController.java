package com.eatda.order.controller;

import com.eatda.order.form.OrderRequestDTO;
import com.eatda.order.form.OrderResponseDTO;
import com.eatda.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    /*
        주문
     */
    @PostMapping
    public ResponseEntity<OrderResponseDTO> addOrder(@RequestBody OrderRequestDTO orderRequestDTO) {
        OrderResponseDTO addedOrder = orderService.createOrder(orderRequestDTO);
        return ResponseEntity.ok(addedOrder);
    }

    /*
        가게ID 별 주문 리스트 조회(사장님이 확인)
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrderList(@PathVariable Long restaurantId) {
        List<OrderResponseDTO> orderList = orderService.getOrderListByRestaurantId(restaurantId);
        return ResponseEntity.ok(orderList);
    }

    /*
        주문ID로 단일 주문 조회 (사장님 또는 아동이 확인)
     */

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDTO> getOrderByOrderId(@PathVariable Long orderId) {
        OrderResponseDTO addedOrder = orderService.getOrderByOrderId(orderId);
        return ResponseEntity.ok(addedOrder);
    }

    /*
        아동ID 별 주문 리스트 조회(아동이 확인)
     */
    @GetMapping("/child/{childId}")
    public ResponseEntity<List<OrderResponseDTO>> getOrdersByChildId(@PathVariable Long childId) {
        List<OrderResponseDTO> orderResponseDTOs = orderService.getOrdersByChildId(childId);
        return ResponseEntity.ok(orderResponseDTOs);
    }
}































