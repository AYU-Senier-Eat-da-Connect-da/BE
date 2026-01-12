package com.eatda.domain.order.entity;

import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Table(name = "orders")
public class Order {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;

    private LocalDateTime orderTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "child_id")
    private Child child;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuOrder> menuOrders;

    /**
     * 주문의 총 가격을 계산합니다.
     */
    public int calculateTotalPrice() {
        int total = 0;
        for (MenuOrder menuOrder : menuOrders) {
            total += menuOrder.getMenu().getPrice() * menuOrder.getMenuCount();
        }
        return total;
    }
}
