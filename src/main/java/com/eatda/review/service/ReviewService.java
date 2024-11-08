package com.eatda.review.service;

import com.eatda.child.domain.Child;
import com.eatda.child.repository.ChildRepository;
import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.repository.RestaurantRepository;
import com.eatda.review.domain.Review;
import com.eatda.review.form.ReviewRequestDTO;
import com.eatda.review.form.ReviewResponseDTO;
import com.eatda.review.repository.ReviewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ChildRepository  childRepository;
    private final RestaurantRepository restaurantRepository;

    private static final int MAX_REVIEW_BODY_LENGTH = 300;

    @Transactional
    public ReviewResponseDTO createReview(Long restaurantId, ReviewRequestDTO reviewRequestDTO, Long childId) {
        if (reviewRequestDTO.getReview_body().length() > MAX_REVIEW_BODY_LENGTH) {
            throw new IllegalArgumentException("리뷰 내용은 300자 이하여야 합니다.");
        }

        Optional<Child> childOptional = childRepository.findById(childId);
        if (childOptional.isPresent()) {
            Child child = childOptional.get();

            Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
            if (restaurantOptional.isPresent()) {
                Restaurant restaurant = restaurantOptional.get();

                Review review = Review.builder()
                        .child(child)
                        .restaurant(restaurant)
                        .review_star(reviewRequestDTO.getReview_star())
                        .review_body(reviewRequestDTO.getReview_body())
                        .createdAt(LocalDateTime.now())
                        .build();
                reviewRepository.save(review);

                return ReviewResponseDTO.builder()
                        .id(review.getId())
                        .childId(child.getId())
                        .restaurantId(restaurant.getId())
                        .review_star(review.getReview_star())
                        .review_body(review.getReview_body())
                        .createdAt(review.getCreatedAt())
                        .build();
            } else {
                throw new RuntimeException("가게를 찾을 수 없습니다.");
            }
        } else {
            throw new RuntimeException("아동을 찾을 수 없습니다.");
        }
    }

    // 아동Id에 해당하는 모든 리뷰 리스트 조회하기
    public List<ReviewResponseDTO> getReviewLIstByChildId(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new RuntimeException("아동을 찾을 수 없습니다."));

        List<Review> reviewList = reviewRepository.findByChildId(childId);

        return reviewList.stream()
                .map(ReviewResponseDTO::toEntity)
                .collect(Collectors.toList());
    }

    // 리뷰Id로 조회하기
    public ReviewResponseDTO getReviewById(Long reviewId) {
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);
        if (reviewOptional.isPresent()) {
            Review review = reviewOptional.get();

            return ReviewResponseDTO.builder()
                    .id(review.getId())
                    .childId(review.getChild().getId())
                    .restaurantId(review.getRestaurant().getId())
                    .review_star(review.getReview_star())
                    .review_body(review.getReview_body())
                    .createdAt(review.getCreatedAt())
                    .build();
        }else {
            throw new RuntimeException("리뷰를 찾을 수 없습니다.");
        }
    }

    // 음식점Id에 해당하는 모든 리뷰 리스트 조회하기
    public List<ReviewResponseDTO> getReviewListByRestaurantId(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("가게를 찾을 수 없습니다."));

        List<Review> reviewList = reviewRepository.findByRestaurantId(restaurantId);

        return reviewList.stream()
                .map(ReviewResponseDTO::toEntity)
                .collect(Collectors.toList());
    }

    // 음식점Id에 해당하는 모든 리뷰 리스트 조회하기
    public List<ReviewResponseDTO> getReviewListByPresidentId(Long presidentId) {
        Restaurant restaurant = restaurantRepository.findFirstByPresidentId(presidentId)
                .orElseThrow(() -> new RuntimeException("해당 presidentId와 연관된 가게를 찾을 수 없습니다."));

        List<Review> reviewList = reviewRepository.findByRestaurantId(restaurant.getId());

        return reviewList.stream()
                .map(ReviewResponseDTO::toEntity)
                .collect(Collectors.toList());
    }

}