package com.eatda.domain.order.entity;

import com.eatda.domain.menu.entity.Menu;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MenuOrder {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    /**
     * Menu 조회 시 @BatchSize로 N+1 최적화
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int menuCount;
}
