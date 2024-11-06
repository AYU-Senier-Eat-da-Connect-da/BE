package com.eatda.order.form;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuOrder {
        private Long menuId;
        private int menuCount; // 주문할 메뉴 수량
    }

    private Long restaurantId;
    private Long childId;
    private List<MenuOrder> menuOrders; // 여러 개의 메뉴 주문 처리
    private int totalsum;
}
