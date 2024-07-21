package com.eatda.review.repository;

import com.eatda.review.domain.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository {

    List<ReviewEntity> findByRestaurantId(int restaurantId);
}
