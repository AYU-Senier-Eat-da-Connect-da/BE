package com.eatda.domain.order.service;

import com.eatda.domain.order.dto.OrderRequestDTO;
import com.eatda.domain.order.dto.OrderResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OptimisticLockOrderFacade {

    private final OrderService orderService;

    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) throws InterruptedException {
        while (true) {
            try {
                return orderService.createOrderWithOptimisticLock(orderRequestDTO);
            } catch (ObjectOptimisticLockingFailureException e) {
                log.info("Optimistic lock collision detected, retrying... menuId: {}", 
                        orderRequestDTO.getMenuOrders().get(0).getMenuId());
                Thread.sleep(50); // 재시도 전 약간의 대기 (Backoff)
            }
        }
    }
}
