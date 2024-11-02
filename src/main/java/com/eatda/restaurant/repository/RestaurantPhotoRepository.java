package com.eatda.restaurant.repository;

import com.eatda.restaurant.domain.RestaurantPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantPhotoRepository extends JpaRepository<RestaurantPhoto, Long> {

}
