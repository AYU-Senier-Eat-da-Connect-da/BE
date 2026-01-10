package com.eatda.domain.order.repository;

import com.eatda.domain.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByChildId(Long childId);

    List<Order> findByRestaurantId(Long restaurantId);
}
