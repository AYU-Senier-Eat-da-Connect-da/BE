package com.eatda.domain.order.repository;

import com.eatda.domain.order.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {

    /**
     * N+1 해결 방식: @BatchSize (Entity에 적용됨)
     *
     * [JOIN FETCH를 사용하지 않은 이유]
     * 1. 페이징 불가: JOIN FETCH + Pageable 사용 시 Hibernate 경고 발생
     *    - "HHH90003004: firstResult/maxResults specified with collection fetch"
     *    - 전체 데이터를 메모리에 로드 후 인메모리 페이징 → OOM 위험
     *
     * 2. 컬렉션 2개 이상 불가: 다중 컬렉션 JOIN FETCH 시 MultipleBagFetchException 발생
     *
     * [@BatchSize 선택 이유]
     * - 페이징과 완벽 호환
     * - IN 절 사용으로 N+1 → 1+1 최적화
     * - 설정 간단, 유지보수 용이
     */
    List<Order> findByChildId(Long childId);

    List<Order> findByRestaurantId(Long restaurantId);

    // 페이징 지원 메서드
    Page<Order> findByChildId(Long childId, Pageable pageable);

    Page<Order> findByRestaurantId(Long restaurantId, Pageable pageable);
}
