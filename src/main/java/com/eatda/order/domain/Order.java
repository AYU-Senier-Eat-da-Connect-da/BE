package com.eatda.order.domain;

import com.eatda.child.domain.Child;
import com.eatda.menu.domain.Menu;
import com.eatda.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
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

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<MenuOrder> menuOrders; // 주문에 포함된 메뉴 목록
}
