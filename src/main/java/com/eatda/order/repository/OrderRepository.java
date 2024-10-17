package com.eatda.order.repository;

import com.eatda.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByChildId(Long childId);

    List<Order> findByRestaurantId(Long restaurantId);
}
