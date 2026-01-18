package com.eatda.domain.restaurant.repository;

import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.dto.RestaurantSearchResult;
import com.eatda.domain.restaurant.entity.Restaurant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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
    Page<RestaurantDTO> findByTextContaining(String text, Pageable pageable);

    @Query(value =
    "SELECT id, restaurant_name AS restaurantName, restaurant_address AS restaurantAddress, " +
            "restaurant_number AS restaurantNumber, restaurant_body AS restaurantBody, " +
            "restaurant_category AS restaurantCategory " +
            "FROM restaurant " +
            "WHERE MATCH(restaurant_name, restaurant_body, restaurant_category) " +
            "AGAINST(:text IN BOOLEAN MODE)",
    nativeQuery = true)
    Slice<RestaurantSearchResult> findByFullTextSearch(@Param("text") String text, Pageable pageable);

    List<Restaurant> findByRestaurantCategory(String restaurantCategory);

    List<Restaurant> findByPresidentId(Long presidentId);

    Optional<Restaurant> findFirstByPresidentId(Long presidentId);
}
