package com.eatda.order.service;

import com.eatda.child.domain.Child;
import com.eatda.child.repository.ChildRepository;
import com.eatda.menu.domain.Menu;
import com.eatda.menu.repository.MenuRepository;
import com.eatda.order.domain.MenuOrder;
import com.eatda.order.domain.Order;
import com.eatda.order.form.OrderRequestDTO;
import com.eatda.order.form.OrderResponseDTO;
import com.eatda.order.repository.OrderRepository;
import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.repository.RestaurantRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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


    /*
        주문 생성 (아동이 주문)
     */
    @Transactional
    public List<OrderResponseDTO> createOrder(OrderRequestDTO orderRequestDTO) {
        Optional<Child> childOptional = childRepository.findById(orderRequestDTO.getChildId());
        if (childOptional.isPresent()) {
            Child child = childOptional.get();

            Optional<Restaurant> restaurantOptional = restaurantRepository.findById(orderRequestDTO.getRestaurantId());
            if (restaurantOptional.isPresent()) {
                Restaurant restaurant = restaurantOptional.get();

                List<OrderResponseDTO> orderResponses = new ArrayList<>();
                for (OrderRequestDTO.MenuOrder menuOrder : orderRequestDTO.getMenuOrders()) {
                    Optional<Menu> menuOptional = menuRepository.findById(menuOrder.getMenuId());
                    if (menuOptional.isPresent()) {
                        Menu menu = menuOptional.get();

                        // MenuOrder 생성
                        MenuOrder newMenuOrder = MenuOrder.builder()
                                .menu(menu)
                                .menuCount(menuOrder.getMenuCount())
                                .build();

                        // Order 생성
                        Order order = Order.builder()
                                .child(child)
                                .restaurant(restaurant)
                                .orderTime(LocalDate.now())
                                .menuOrders(List.of(newMenuOrder)) // MenuOrder 리스트 추가
                                .build();
                        orderRepository.save(order);

                        orderResponses.add(OrderResponseDTO.builder()
                                .id(order.getId())
                                .restaurantId(restaurant.getId())
                                .childId(child.getId())
                                .menuOrders(List.of(OrderResponseDTO.MenuOrder.builder()
                                        .menuId(menu.getId())
                                        .menuCount(newMenuOrder.getMenuCount())
                                        .build()))
                                .orderTime(order.getOrderTime())
                                .build());
                    } else {
                        throw new RuntimeException("메뉴를 찾을 수 없습니다.");
                    }
                }
                return orderResponses;
            } else {
                throw new RuntimeException("가게를 찾을 수 없습니다.");
            }
        } else {
            throw new RuntimeException("아동을 찾을 수 없습니다.");
        }
    }


    /*
        주문받은 리스트로 전체 조회 (사장님 마이페이지 - 가게관리 - 들어온 주문 모두 보기)
     */
    public List<OrderResponseDTO> getOrderList(Long restaurantId) {
        List<Order> orderList = orderRepository.findByRestaurantId(restaurantId); // 가게 ID로 주문 조회

        return orderList.stream()
                .map(order -> OrderResponseDTO.builder()
                        .id(order.getId())
                        .restaurantId(order.getRestaurant().getId())
                        .childId(order.getChild().getId())
                        .menuOrders(order.getMenuOrders().stream() // MenuOrders로 변경
                                .map(menuOrder -> OrderResponseDTO.MenuOrder.builder()
                                        .menuId(menuOrder.getMenu().getId())
                                        .menuCount(menuOrder.getMenuCount())
                                        .build())
                                .collect(Collectors.toList()))
                        .orderTime(order.getOrderTime())
                        .build())
                .collect(Collectors.toList());
    }


    /*
        주문 ID로 조회하기
        (사장님 마이페이지 - 가게관리 - 들어온 주문 보기 - 상세클릭)
        (아동 마이페이지 - 주문 내역보기 - 상세클릭)
     */
    public OrderResponseDTO getOrderByOrderId(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("주문을 찾을 수 없습니다."));

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
                .price(order.getMenuOrders().stream() // 가격 계산
                        .mapToInt(menuOrder -> menuOrder.getMenu().getPrice() * menuOrder.getMenuCount())
                        .sum())
                .build();
    }


    /*
        아동 ID 별로 모든 주문 조회 (아동 마이페이지 - 주문 내역보기)
     */
    public List<OrderResponseDTO> getOrdersByChildId(Long childId) {
        List<Order> orders = orderRepository.findByChildId(childId);
        if (orders.isEmpty()) {
            throw new RuntimeException("주문을 찾을 수 없습니다.");
        }

        return orders.stream()
                .map(order -> OrderResponseDTO.builder()
                        .id(order.getId())
                        .restaurantId(order.getRestaurant().getId())
                        .childId(order.getChild().getId())
                        .menuOrders(order.getMenuOrders().stream() // MenuOrders로 변경
                                .map(menuOrder -> OrderResponseDTO.MenuOrder.builder()
                                        .menuId(menuOrder.getMenu().getId())
                                        .menuCount(menuOrder.getMenuCount())
                                        .build())
                                .collect(Collectors.toList()))
                        .orderTime(order.getOrderTime())
                        .price(order.getMenuOrders().stream() // 가격 계산
                                .mapToInt(menuOrder -> menuOrder.getMenu().getPrice() * menuOrder.getMenuCount())
                                .sum())
                        .build())
                .collect(Collectors.toList());
    }
}