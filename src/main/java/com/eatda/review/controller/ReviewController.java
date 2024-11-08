package com.eatda.review.controller;

import com.eatda.review.form.ReviewRequestDTO;
import com.eatda.review.form.ReviewResponseDTO;
import com.eatda.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

    /*
        리뷰 작성
     */
    @PostMapping("/restaurant/{restaurantId}/addReview")
    public ResponseEntity<ReviewResponseDTO> addReview(
            @PathVariable Long restaurantId,
            @RequestBody ReviewRequestDTO reviewRequestDto,
            @RequestParam Long childId) {

        ReviewResponseDTO addedReview = reviewService.createReview(restaurantId, reviewRequestDto, childId);
        if (addedReview != null) {
            return ResponseEntity.ok(addedReview);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
        아동ID 별로 리뷰 리스트 조회
     */
    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewListByChildId(@PathVariable Long childId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewLIstByChildId(childId);
        return ResponseEntity.ok(reviews);
    }

    /*
        리뷰ID 별로 특정 리뷰 조회
     */
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long reviewId) {
        ReviewResponseDTO review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

    /*
        음식점ID 별로 특정 가게의 모든 리뷰 조회
     */
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByRestaurantId(@PathVariable Long restaurantId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewListByRestaurantId(restaurantId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/president/{presidentId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByPresidentId(@PathVariable Long presidentId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewListByPresidentId(presidentId);
        return ResponseEntity.ok(reviews);
    }




}