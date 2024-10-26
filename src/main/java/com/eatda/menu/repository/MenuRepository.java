package com.eatda.menu.repository;

import com.eatda.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    List<Menu> findByRestaurantId(Long restaurantId);

    @Query("SELECT m FROM Menu m JOIN m.restaurant r WHERE r.president.id = :presidentId")
    List<Menu> findByPresidentId(@Param("presidentId") Long presidentId);
}
