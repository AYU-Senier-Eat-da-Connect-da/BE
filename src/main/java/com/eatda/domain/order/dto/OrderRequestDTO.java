package com.eatda.domain.order.dto;

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
        private int menuCount;
    }

    private Long restaurantId;
    private Long childId;
    private List<MenuOrder> menuOrders;
    private int totalsum;
}
