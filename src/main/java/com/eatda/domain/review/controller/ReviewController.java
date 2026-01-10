package com.eatda.domain.review.controller;

import com.eatda.domain.review.dto.ReviewRequestDTO;
import com.eatda.domain.review.dto.ReviewResponseDTO;
import com.eatda.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/reviews")
public class ReviewController {

    private final ReviewService reviewService;

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

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewListByChildId(@PathVariable Long childId) {
        List<ReviewResponseDTO> reviews = reviewService.getReviewLIstByChildId(childId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long reviewId) {
        ReviewResponseDTO review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(review);
    }

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
