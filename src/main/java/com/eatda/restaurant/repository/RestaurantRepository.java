package com.eatda.restaurant.repository;

import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.domain.RestaurantDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    // JPQL 쿼리로 메뉴 이름을 포함하는 레스토랑 검색
    @Query("SELECT r FROM Restaurant r JOIN r.menus m WHERE m.menuName LIKE %:menuName%")
    List<Restaurant> findByMenuNameContaining(String menuName);

    // JPQL 쿼리로 이름, 정보, 카테고리에 TEXT가 들어가면 검색
    @Query("SELECT new com.eatda.restaurant.domain.RestaurantDTO(r.id, r.restaurantName, r.restaurantAddress, r.restaurantNumber, r.restaurantBody, r.restaurantCategory) " +
            "FROM Restaurant r WHERE r.restaurantName LIKE %:text% " +
            "OR r.restaurantBody LIKE %:text% " +
            "OR r.restaurantCategory LIKE %:text%")
    List<RestaurantDTO> findByTextContaining(String text);

    List<Restaurant> findByRestaurantCategory(String restaurantCategory);

    List<Restaurant> findByPresidentId(Long presidentId);

    Optional<Restaurant> findFirstByPresidentId(Long presidentId);


}
