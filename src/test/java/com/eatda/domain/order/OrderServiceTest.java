package com.eatda.domain.order;

import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.menu.repository.MenuRepository;
import com.eatda.domain.order.dto.OrderRequestDTO;
import com.eatda.domain.order.dto.OrderResponseDTO;
import com.eatda.domain.order.repository.OrderRepository;
import com.eatda.domain.order.service.OrderService;
import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ChildRepository childRepository;
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private MenuRepository menuRepository;


    private Child testChild;
    private Restaurant testRestaurant;
    private Menu testMenu1;
    private Menu testMenu2;

    @BeforeEach
    void setUp() {

        //테스트용 아동
        testChild = Child.builder()
                .childName("신짱구")
                .childAmount(50000)
                .build();
        childRepository.save(testChild);

        //테스트용 가게
        testRestaurant = Restaurant.builder()
                .restaurantName("건강밥상 심마니 평촌")
                .build();
        restaurantRepository.save(testRestaurant);

        // 테스트용 메뉴들
        testMenu1 = Menu.builder()
                .menuName("비빔밥")
                .price(8000)
                .restaurant(testRestaurant)
                .build();
        menuRepository.save(testMenu1);

        testMenu2 = Menu.builder()
                .menuName("된장찌개")
                .price(7000)
                .restaurant(testRestaurant)
                .build();
        menuRepository.save(testMenu2);
    }

    @Test
    @DisplayName("주문이 정상 등록되면 성공")
    void createOrder() throws Exception {
        //Given: 주문 요청 데이터 생성
        OrderRequestDTO.MenuOrder menuOrder1 = OrderRequestDTO.MenuOrder.builder()
                .menuId(testMenu1.getId())
                .menuCount(1)
                .build();

        OrderRequestDTO.MenuOrder menuOrder2 = OrderRequestDTO.MenuOrder.builder()
                .menuId(testMenu2.getId())
                .menuCount(2)
                .build();

        int totalSum = (testMenu1.getPrice() * 1) + (testMenu2.getPrice() * 2);

        OrderRequestDTO orderRequestDTO = OrderRequestDTO.builder()
                .childId(testChild.getId())
                .restaurantId(testRestaurant.getId())
                .menuOrders(List.of(menuOrder1, menuOrder2))
                .totalsum(totalSum) // 총 결제 금액 설정
                .build();

        // When: 주문 생성
        OrderResponseDTO orderResponseDTO = orderService.createOrder(orderRequestDTO);

        // Then: 주문 생성 확인
        assertNotNull(orderResponseDTO);

        // 첫 번째 메뉴 주문 확인
        assertEquals(testChild.getId(), orderResponseDTO.getChildId());
        assertEquals(testRestaurant.getId(), orderResponseDTO.getRestaurantId());
        assertEquals(2, orderResponseDTO.getMenuOrders().size()); // 메뉴 주문 수 확인
        assertEquals(testMenu1.getId(), orderResponseDTO.getMenuOrders().get(0).getMenuId());
        assertEquals(1, orderResponseDTO.getMenuOrders().get(0).getMenuCount());

         // 두 번째 메뉴 주문 확인
        assertEquals(testMenu2.getId(), orderResponseDTO.getMenuOrders().get(1).getMenuId());
        assertEquals(2, orderResponseDTO.getMenuOrders().get(1).getMenuCount());
    }
}