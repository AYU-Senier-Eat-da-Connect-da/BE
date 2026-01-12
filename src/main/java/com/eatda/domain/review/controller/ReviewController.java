package com.eatda.domain.review.controller;

import com.eatda.domain.review.dto.ReviewRequestDTO;
import com.eatda.domain.review.dto.ReviewResponseDTO;
import com.eatda.domain.review.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
        return new ResponseEntity<>(reviewService.createReview(restaurantId, reviewRequestDto, childId), HttpStatus.CREATED);
    }

    @GetMapping("/child/{childId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewListByChildId(@PathVariable Long childId) {
        return ResponseEntity.ok(reviewService.getReviewLIstByChildId(childId));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewResponseDTO> getReviewById(@PathVariable Long reviewId) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId));
    }

    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByRestaurantId(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(reviewService.getReviewListByRestaurantId(restaurantId));
    }

    @GetMapping("/president/{presidentId}")
    public ResponseEntity<List<ReviewResponseDTO>> getReviewsByPresidentId(@PathVariable Long presidentId) {
        return ResponseEntity.ok(reviewService.getReviewListByPresidentId(presidentId));
    }
}
