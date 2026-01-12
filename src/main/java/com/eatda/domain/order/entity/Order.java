package com.eatda.domain.order.entity;

import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.BatchSize;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    /**
     * N+1 해결: @BatchSize 적용
     *
     * [JOIN FETCH를 사용하지 않은 이유]
     * - JOIN FETCH는 컬렉션 페치 시 페이징(LIMIT, OFFSET)과 함께 사용할 수 없음
     * - Hibernate 경고: "HHH90003004: firstResult/maxResults specified with collection fetch"
     * - 전체 데이터를 메모리에 로드 후 인메모리 페이징 → 메모리 폭발 위험
     *
     * [@BatchSize 선택 이유]
     * - 페이징과 호환 가능
     * - N+1 쿼리를 1+1 쿼리로 최적화 (IN 절 사용)
     * - 코드 변경 최소화, 유지보수 용이
     * - size=100: 한 번에 100개씩 배치로 조회
     */
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @BatchSize(size = 100)
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
