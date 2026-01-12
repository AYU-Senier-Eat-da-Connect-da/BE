package com.eatda.domain.review.service;

import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import com.eatda.domain.review.dto.ReviewRequestDTO;
import com.eatda.domain.review.dto.ReviewResponseDTO;
import com.eatda.domain.review.entity.Review;
import com.eatda.domain.review.repository.ReviewRepository;
import com.eatda.global.exception.CustomException;
import com.eatda.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final ChildRepository childRepository;

    @Transactional
    public ReviewResponseDTO createReview(Long restaurantId, ReviewRequestDTO reviewRequestDTO, Long childId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));

        Review review = Review.builder()
                .review_star(reviewRequestDTO.getReview_star())
                .review_body(reviewRequestDTO.getReview_body())
                .restaurant(restaurant)
                .child(child)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review);
        return ReviewResponseDTO.from(review);
    }

    public List<ReviewResponseDTO> getReviewLIstByChildId(Long childId) {
        List<Review> reviewList = reviewRepository.findByChildId(childId);
        return ReviewResponseDTO.from(reviewList);
    }

    public ReviewResponseDTO getReviewById(Long reviewId) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        return ReviewResponseDTO.from(review);
    }

    public List<ReviewResponseDTO> getReviewListByRestaurantId(Long restaurantId) {
        List<Review> reviewList = reviewRepository.findByRestaurantId(restaurantId);

        List<ReviewResponseDTO> responses = new ArrayList<>();

        for(Review review : reviewList){
            ReviewResponseDTO dto = ReviewResponseDTO.builder()
                    .id(review.getId())
                    .review_star(review.getReview_star())
                    .review_body(review.getReview_body())
                    .childId(review.getChild().getId())
                    .restaurantId(review.getRestaurant().getId())
                    .createdAt(review.getCreatedAt())
                    .build();

            responses.add(dto);
        }

        return responses;
    }

    public List<ReviewResponseDTO> getReviewListByPresidentId(Long presidentId) {
        Restaurant restaurant = restaurantRepository.findFirstByPresidentId(presidentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        List<Review> reviewList = reviewRepository.findByRestaurantId(restaurant.getId());
        return ReviewResponseDTO.from(reviewList);
    }
}
