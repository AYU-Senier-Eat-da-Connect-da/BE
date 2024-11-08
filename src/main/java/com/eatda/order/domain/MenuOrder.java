package com.eatda.order.domain;

import com.eatda.menu.domain.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MenuOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order; // 주문과의 관계

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "menu_id")
    private Menu menu; // 메뉴와의 관계

    private int menuCount; // 주문할 메뉴 수량

}
