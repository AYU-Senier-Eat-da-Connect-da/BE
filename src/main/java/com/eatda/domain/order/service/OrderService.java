package com.eatda.domain.order.service;

import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.menu.repository.MenuRepository;
import com.eatda.domain.order.entity.MenuOrder;
import com.eatda.domain.order.entity.Order;
import com.eatda.domain.order.dto.OrderRequestDTO;
import com.eatda.domain.order.dto.OrderResponseDTO;
import com.eatda.domain.order.repository.OrderRepository;
import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import com.eatda.global.exception.CustomException;
import com.eatda.global.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;
    private final ChildRepository childRepository;
    private final MenuRepository menuRepository;

    @Transactional
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequestDTO) {
        // 아동 조회 및 결제
        Child child = childRepository.findById(orderRequestDTO.getChildId())
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));

        // DDD: 도메인 메서드를 통한 결제 (잔액 검증 및 차감이 Entity 내에 캡슐화)
        child.pay(orderRequestDTO.getTotalsum());

        // 식당 조회
        Restaurant restaurant = restaurantRepository.findById(orderRequestDTO.getRestaurantId())
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        // 주문 생성
        Order order = Order.builder()
                .child(child)
                .restaurant(restaurant)
                .orderTime(LocalDateTime.now())
                .menuOrders(new ArrayList<>())
                .build();

        // 메뉴 추가
        for (OrderRequestDTO.MenuOrder menuOrderRequest : orderRequestDTO.getMenuOrders()) {
            Menu menu = menuRepository.findById(menuOrderRequest.getMenuId())
                    .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

            MenuOrder newMenuOrder = MenuOrder.builder()
                    .menu(menu)
                    .menuCount(menuOrderRequest.getMenuCount())
                    .order(order)
                    .build();

            order.getMenuOrders().add(newMenuOrder);
        }

        orderRepository.save(order);

        // 응답 생성
        int totalPrice = order.getMenuOrders().stream()
                .mapToInt(menuOrder -> menuOrder.getMenu().getPrice() * menuOrder.getMenuCount())
                .sum();

        return OrderResponseDTO.builder()
                .id(order.getId())
                .restaurantId(restaurant.getId())
                .childId(child.getId())
                .menuOrders(order.getMenuOrders().stream()
                        .map(menuOrder -> OrderResponseDTO.MenuOrder.builder()
                                .menuId(menuOrder.getMenu().getId())
                                .menuCount(menuOrder.getMenuCount())
                                .build())
                        .collect(Collectors.toList()))
                .orderTime(order.getOrderTime())
                .price(totalPrice)
                .build();
    }

    public List<OrderResponseDTO> getOrderListByRestaurantId(Long restaurantId) {
        List<Order> orderList = orderRepository.findByRestaurantId(restaurantId);

        return orderList.stream()
                .map(order -> OrderResponseDTO.builder()
                        .id(order.getId())
                        .restaurantId(order.getRestaurant().getId())
                        .childId(order.getChild().getId())
                        .menuOrders(order.getMenuOrders().stream()
                                .map(menuOrder -> OrderResponseDTO.MenuOrder.builder()
                                        .menuId(menuOrder.getMenu().getId())
                                        .menuCount(menuOrder.getMenuCount())
                                        .build())
                                .collect(Collectors.toList()))
                        .orderTime(order.getOrderTime())
                        .price(order.getMenuOrders().stream()
                                .mapToInt(menuOrder -> menuOrder.getMenu().getPrice() * menuOrder.getMenuCount())
                                .sum())
                        .build())
                .collect(Collectors.toList());
    }

    public OrderResponseDTO getOrderByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        return OrderResponseDTO.builder()
                .id(order.getId())
                .restaurantId(order.getRestaurant().getId())
                .childId(order.getChild().getId())
                .menuOrders(order.getMenuOrders().stream()
                        .map(menuOrder -> OrderResponseDTO.MenuOrder.builder()
                                .menuId(menuOrder.getMenu().getId())
                                .menuCount(menuOrder.getMenuCount())
                                .build())
                        .collect(Collectors.toList()))
                .orderTime(order.getOrderTime())
                .price(order.getMenuOrders().stream()
                        .mapToInt(menuOrder -> menuOrder.getMenu().getPrice() * menuOrder.getMenuCount())
                        .sum())
                .build();
    }

    public List<OrderResponseDTO> getOrdersByChildId(Long childId) {
        List<Order> orders = orderRepository.findByChildId(childId);
        if (orders.isEmpty()) {
            throw new CustomException(ErrorCode.ORDER_NOT_FOUND);
        }

        return orders.stream()
                .map(order -> OrderResponseDTO.builder()
                        .id(order.getId())
                        .restaurantId(order.getRestaurant().getId())
                        .restaurantName(order.getRestaurant().getRestaurantName())
                        .childId(order.getChild().getId())
                        .menuOrders(order.getMenuOrders().stream()
                                .map(menuOrder -> OrderResponseDTO.MenuOrder.builder()
                                        .menuId(menuOrder.getMenu().getId())
                                        .menuName(menuOrder.getMenu().getMenuName())
                                        .menuBody(menuOrder.getMenu().getMenuBody())
                                        .menuCount(menuOrder.getMenuCount())
                                        .build())
                                .collect(Collectors.toList()))
                        .orderTime(order.getOrderTime())
                        .price(order.getMenuOrders().stream()
                                .mapToInt(menuOrder -> menuOrder.getMenu().getPrice() * menuOrder.getMenuCount())
                                .sum())
                        .build())
                .collect(Collectors.toList());
    }

    public List<OrderResponseDTO> getOrderListByPresidentId(Long presidentId) {
        List<Restaurant> restaurants = restaurantRepository.findByPresidentId(presidentId);

        if (restaurants.isEmpty()) {
            throw new IllegalArgumentException("No restaurants found for presidentId: " + presidentId);
        }

        Restaurant restaurant = restaurants.get(0);
        Long restaurantId = restaurant.getId();

        List<Order> orderList = orderRepository.findByRestaurantId(restaurantId);

        return orderList.stream()
                .map(order -> OrderResponseDTO.builder()
                        .id(order.getId())
                        .restaurantId(order.getRestaurant().getId())
                        .restaurantName(order.getRestaurant().getRestaurantName())
                        .childId(order.getChild().getId())
                        .child(OrderResponseDTO.Child.builder()
                                .id(order.getChild().getId())
                                .name(order.getChild().getChildName())
                                .email(order.getChild().getChildEmail())
                                .phone(order.getChild().getChildNumber())
                                .address(order.getChild().getChildAddress())
                                .build())
                        .menuOrders(order.getMenuOrders().stream()
                                .map(menuOrder -> OrderResponseDTO.MenuOrder.builder()
                                        .menuId(menuOrder.getMenu().getId())
                                        .menuCount(menuOrder.getMenuCount())
                                        .menuName(menuOrder.getMenu().getMenuName())
                                        .menuBody(menuOrder.getMenu().getMenuBody())
                                        .build())
                                .collect(Collectors.toList()))
                        .orderTime(order.getOrderTime())
                        .price(order.getMenuOrders().stream()
                                .mapToInt(menuOrder -> menuOrder.getMenu().getPrice() * menuOrder.getMenuCount())
                                .sum())
                        .build())
                .collect(Collectors.toList());
    }
}
