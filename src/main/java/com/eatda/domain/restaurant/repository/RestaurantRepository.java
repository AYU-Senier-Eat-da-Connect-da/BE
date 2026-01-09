package com.eatda.domain.restaurant.repository;

import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.menuName LIKE %:menuName%")
    List<Restaurant> findByMenuNameContaining(String menuName);

    @Query("SELECT new com.eatda.domain.restaurant.dto.RestaurantDTO(r.id, r.restaurantName, r.restaurantAddress, r.restaurantNumber, r.restaurantBody, r.restaurantCategory) " +
            "FROM Restaurant r WHERE r.restaurantName LIKE %:text% " +
            "OR r.restaurantBody LIKE %:text% " +
            "OR r.restaurantCategory LIKE %:text%")
    List<RestaurantDTO> findByTextContaining(String text);

    List<Restaurant> findByRestaurantCategory(String restaurantCategory);

    List<Restaurant> findByPresidentId(Long presidentId);

    Optional<Restaurant> findFirstByPresidentId(Long presidentId);
}
