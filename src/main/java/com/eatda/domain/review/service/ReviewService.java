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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;
    private final ChildRepository childRepository;

    @Transactional
    public ReviewResponseDTO createReview(Long restaurantId, ReviewRequestDTO reviewRequestDTO, Long childId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        Optional<Child> childOptional = childRepository.findById(childId);

        if (restaurantOptional.isPresent() && childOptional.isPresent()) {
            Restaurant restaurant = restaurantOptional.get();
            Child child = childOptional.get();

            Review review = Review.builder()
                    .review_star(reviewRequestDTO.getReview_star())
                    .review_body(reviewRequestDTO.getReview_body())
                    .restaurant(restaurant)
                    .child(child)
                    .createdAt(LocalDateTime.now())
                    .build();

            reviewRepository.save(review);
            return ReviewResponseDTO.toEntity(review);
        }
        return null;
    }

    public List<ReviewResponseDTO> getReviewLIstByChildId(Long childId) {
        List<Review> reviewList = reviewRepository.findByChildId(childId);
        return reviewList.stream()
                .map(ReviewResponseDTO::toEntity)
                .collect(Collectors.toList());
    }

    public ReviewResponseDTO getReviewById(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            return ReviewResponseDTO.toEntity(reviewOptional.get());
        }
        throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
    }

    public List<ReviewResponseDTO> getReviewListByRestaurantId(Long restaurantId) {
        List<Review> reviewList = reviewRepository.findByRestaurantId(restaurantId);
        return reviewList.stream()
                .map(ReviewResponseDTO::toEntity)
                .collect(Collectors.toList());
    }

    public List<ReviewResponseDTO> getReviewListByPresidentId(Long presidentId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findFirstByPresidentId(presidentId);

        if (restaurantOptional.isPresent()) {
            Long restaurantId = restaurantOptional.get().getId();
            List<Review> reviewList = reviewRepository.findByRestaurantId(restaurantId);

            return reviewList.stream()
                    .map(ReviewResponseDTO::toEntity)
                    .collect(Collectors.toList());
        }
        throw new CustomException(ErrorCode.RESTAURANT_NOT_FOUND);
    }
}
