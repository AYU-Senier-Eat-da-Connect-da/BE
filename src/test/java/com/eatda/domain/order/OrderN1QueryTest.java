package com.eatda.domain.order;

import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.menu.repository.MenuRepository;
import com.eatda.domain.order.dto.OrderResponseDTO;
import com.eatda.domain.order.entity.MenuOrder;
import com.eatda.domain.order.entity.Order;
import com.eatda.domain.order.repository.OrderRepository;
import com.eatda.domain.order.service.OrderService;
import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrderN1QueryTest {

    @Autowired private OrderService orderService;
    @Autowired private OrderRepository orderRepository;
    @Autowired private ChildRepository childRepository;
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private MenuRepository menuRepository;
    @Autowired private EntityManager em;

    private Child testChild;
    private Restaurant testRestaurant;
    private Menu testMenu1;
    private Menu testMenu2;

    @BeforeEach
    void setUp() {
        // 테스트용 아동
        testChild = Child.builder()
                .childName("신짱구")
                .childAmount(100000)
                .build();
        childRepository.save(testChild);

        // 테스트용 가게
        testRestaurant = Restaurant.builder()
                .restaurantName("건강밥상 심마니 평촌")
                .build();
        restaurantRepository.save(testRestaurant);

        // 테스트용 메뉴들
        testMenu1 = Menu.builder()
                .menuName("비빔밥")
                .menuBody("맛있는 비빔밥")
                .price(8000)
                .restaurant(testRestaurant)
                .build();
        testMenu2 = Menu.builder()
                .menuName("된장찌개")
                .menuBody("구수한 된장찌개")
                .price(7000)
                .restaurant(testRestaurant)
                .build();
        menuRepository.save(testMenu1);
        menuRepository.save(testMenu2);

        // 주문 3개 생성 (N+1 재현을 위해 여러 개)
        for (int i = 0; i < 3; i++) {
            Order order = Order.builder()
                    .child(testChild)
                    .restaurant(testRestaurant)
                    .orderTime(LocalDateTime.now())
                    .menuOrders(new ArrayList<>())
                    .build();

            MenuOrder menuOrder1 = MenuOrder.builder()
                    .order(order)
                    .menu(testMenu1)
                    .menuCount(1)
                    .build();
            MenuOrder menuOrder2 = MenuOrder.builder()
                    .order(order)
                    .menu(testMenu2)
                    .menuCount(2)
                    .build();

            order.getMenuOrders().add(menuOrder1);
            order.getMenuOrders().add(menuOrder2);

            orderRepository.save(order);
        }

        // 영속성 컨텍스트 초기화 - Lazy Loading 강제
        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("N+1 문제 재현 - 아동 ID로 주문 조회")
    void findOrdersByChildId_N_Plus_1_Problem() {
        System.out.println("========== N+1 쿼리 시작 ==========");


        List<OrderResponseDTO> orders = orderService.getOrdersByChildId(testChild.getId());

        System.out.println("========== N+1 쿼리 끝 ==========");

        // 검증
        assertEquals(3, orders.size());

        for (OrderResponseDTO order : orders) {
            System.out.println("주문 ID: " + order.getId() +
                    ", 식당: " + order.getRestaurantName() +
                    ", 메뉴 수: " + order.getMenuOrders().size() +
                    ", 총 가격: " + order.getPrice());
        }

        // then
        /**
         *
         * 아동이 자신의 주문 목록을 조회한다. -> 주문 메뉴를 조회하면 메뉴 목록을 볼 수 있다.
         * select o1_0.order_id,o1_0.child_id,o1_0.order_time,o1_0.restaurant_id from orders o1_0 where o1_0.child_id=?
         * select c1_0.id,c1_0.child_address,c1_0.child_amount,c1_0.child_email,c1_0.child_name,c1_0.child_number,c1_0.child_password,c1_0.fcm_token,s1_0.id,s1_0.fcm_token,s1_0.sponsor_address,s1_0.sponsor_amount,s1_0.sponsor_email,s1_0.sponsor_name,s1_0.sponsor_number,s1_0.sponsor_password from child c1_0 left join sponsor s1_0 on s1_0.id=c1_0.sponsor_id where c1_0.id=?
         * select r1_0.id,r1_0.president_id,r1_0.restaurant_address,r1_0.restaurant_body,r1_0.restaurant_category,r1_0.restaurant_name,r1_0.restaurant_number from restaurant r1_0 where r1_0.id=?
         * select r1_0.id,r1_0.president_id,r1_0.restaurant_address,r1_0.restaurant_body,r1_0.restaurant_category,r1_0.restaurant_name,r1_0.restaurant_number from restaurant r1_0 where r1_0.id=?
         * select mo1_0.order_id,mo1_0.menu_order_id,m1_0.id,m1_0.menu_body,m1_0.menu_name,m1_0.menu_status,m1_0.price,m1_0.restaurant_id,mo1_0.menu_count from menu_order mo1_0 left join menu m1_0 on m1_0.id=mo1_0.menu_id where mo1_0.order_id=?
         * select mo1_0.order_id,mo1_0.menu_order_id,m1_0.id,m1_0.menu_body,m1_0.menu_name,m1_0.menu_status,m1_0.price,m1_0.restaurant_id,mo1_0.menu_count from menu_order mo1_0 left join menu m1_0 on m1_0.id=mo1_0.menu_id where mo1_0.order_id=?
         * select mo1_0.order_id,mo1_0.menu_order_id,m1_0.id,m1_0.menu_body,m1_0.menu_name,m1_0.menu_status,m1_0.price,m1_0.restaurant_id,mo1_0.menu_count from menu_order mo1_0 left join menu m1_0 on m1_0.id=mo1_0.menu_id where mo1_0.order_id=?
         */

        /**
         * select distinct
         *  o1_0.order_id,
         *  c1_0.id,
         *  c1_0.child_address,
         *  c1_0.child_amount,
         *  c1_0.child_email,
         *  c1_0.child_name,
         *  c1_0.child_number,
         *  c1_0.child_password,
         *  c1_0.fcm_token,
         *  c1_0.sponsor_id,
         *  mo1_0.order_id,
         *  mo1_0.menu_order_id,
         *  m1_0.id,
         *  m1_0.menu_body,
         *  m1_0.menu_name,
         *  m1_0.menu_status,
         *  m1_0.price,
         *  m1_0.restaurant_id,
         *  mo1_0.menu_count,
         *  o1_0.order_time,
         *  r1_0.id,
         *  r1_0.president_id,
         *  r1_0.restaurant_address,
         *  r1_0.restaurant_body,
         *  r1_0.restaurant_category,
         *  r1_0.restaurant_name,
         *  r1_0.restaurant_number
         *  from orders o1_0 join restaurant r1_0 on r1_0.id=o1_0.restaurant_id
         *                  join child c1_0 on c1_0.id=o1_0.child_id
         *                  join menu_order mo1_0 on o1_0.order_id=mo1_0.order_id
         *                  join menu m1_0 on m1_0.id=mo1_0.menu_id
         *                  where o1_0.child_id=?
         */
    }
}
