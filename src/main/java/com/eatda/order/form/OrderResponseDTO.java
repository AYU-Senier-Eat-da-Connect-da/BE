package com.eatda.order.form;

import com.eatda.child.domain.Child;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDTO {

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MenuOrder {
        private Long menuId;
        private int menuCount; //  주문할 메뉴 수량
        private String menuName;
        private String menuBody;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Child {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;
    }

    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private Long childId;
    private Child child;
    private List<MenuOrder> menuOrders; // 여러 개의 메뉴 주문
    private LocalDateTime orderTime;
    private int price;  //주문 가격
}
