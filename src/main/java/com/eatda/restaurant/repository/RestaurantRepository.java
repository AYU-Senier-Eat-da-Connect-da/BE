package com.eatda.restaurant.repository;

import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.domain.RestaurantDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // JPQL 쿼리로 메뉴 이름을 포함하는 레스토랑 검색
    @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.menuName LIKE %:menuName%")
    List<Restaurant> findByMenuNameContaining(String menuName);

    List<Restaurant> findByPresidentId(Long presidentId);

    Optional<Restaurant> findFirstByPresidentId(Long presidentId);
}
