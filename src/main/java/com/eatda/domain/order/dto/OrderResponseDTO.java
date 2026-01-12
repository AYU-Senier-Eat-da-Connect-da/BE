package com.eatda.domain.order.dto;

import com.eatda.domain.order.entity.Order;
import com.eatda.domain.order.entity.MenuOrder;
import com.eatda.domain.user.child.entity.Child;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public static class MenuOrderDTO {
        private Long menuId;
        private int menuCount;
        private String menuName;
        private String menuBody;

        public static MenuOrderDTO from(MenuOrder menuOrder) {
            return MenuOrderDTO.builder()
                    .menuId(menuOrder.getMenu().getId())
                    .menuCount(menuOrder.getMenuCount())
                    .menuName(menuOrder.getMenu().getMenuName())
                    .menuBody(menuOrder.getMenu().getMenuBody())
                    .build();
        }

        public static List<MenuOrderDTO> from(List<MenuOrder> menuOrders) {
            List<MenuOrderDTO> result = new ArrayList<>();
            for (MenuOrder menuOrder : menuOrders) {
                result.add(from(menuOrder));
            }
            return result;
        }
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ChildDTO {
        private Long id;
        private String name;
        private String email;
        private String phone;
        private String address;

        public static ChildDTO from(Child child) {
            return ChildDTO.builder()
                    .id(child.getId())
                    .name(child.getChildName())
                    .email(child.getChildEmail())
                    .phone(child.getChildNumber())
                    .address(child.getChildAddress())
                    .build();
        }
    }

    private Long id;
    private Long restaurantId;
    private String restaurantName;
    private Long childId;
    private ChildDTO child;
    private List<MenuOrderDTO> menuOrders;
    private LocalDateTime orderTime;
    private int price;

    /**
     * 기본 변환: Order -> OrderResponseDTO
     */
    public static OrderResponseDTO from(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .restaurantId(order.getRestaurant().getId())
                .restaurantName(order.getRestaurant().getRestaurantName())
                .childId(order.getChild().getId())
                .menuOrders(MenuOrderDTO.from(order.getMenuOrders()))
                .orderTime(order.getOrderTime())
                .price(order.calculateTotalPrice())
                .build();
    }

    /**
     * 상세 변환 (Child 정보 포함): Order -> OrderResponseDTO
     */
    public static OrderResponseDTO fromWithChild(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .restaurantId(order.getRestaurant().getId())
                .restaurantName(order.getRestaurant().getRestaurantName())
                .childId(order.getChild().getId())
                .child(ChildDTO.from(order.getChild()))
                .menuOrders(MenuOrderDTO.from(order.getMenuOrders()))
                .orderTime(order.getOrderTime())
                .price(order.calculateTotalPrice())
                .build();
    }

    /**
     * List 변환: List<Order> -> List<OrderResponseDTO>
     */
    public static List<OrderResponseDTO> from(List<Order> orders) {
        List<OrderResponseDTO> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(from(order));
        }
        return result;
    }

    /**
     * List 변환 (Child 정보 포함): List<Order> -> List<OrderResponseDTO>
     */
    public static List<OrderResponseDTO> fromWithChild(List<Order> orders) {
        List<OrderResponseDTO> result = new ArrayList<>();
        for (Order order : orders) {
            result.add(fromWithChild(order));
        }
        return result;
    }
}
