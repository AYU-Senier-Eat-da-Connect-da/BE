package com.eatda.review.controller;

import com.eatda.review.domain.ReviewRequestDto;
import com.eatda.review.domain.ReviewResponseDto;
import com.eatda.review.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/{restaurant_id}/review")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    //리뷰 등록
    @PostMapping("/")
    public ResponseEntity<String> review_write(@PathVariable("restaurant_id") int restaurant_id,
                                               @RequestBody ReviewRequestDto reviewRequestDto) {
        reviewRequestDto.setRestaurant_id(restaurant_id);   //리뷰 dto에 음식정 id 설정

        boolean isReviewWirtten = reviewService.review_write(reviewRequestDto);
        if(isReviewWirtten) {
            return ResponseEntity.ok().body("리뷰 작성 완료");
        } else {
            return ResponseEntity.badRequest().body("리뷰 등록 실패");
        }
}

    //리뷰 리스트 조회
    @GetMapping("/")
    public ResponseEntity<List<ReviewResponseDto>> review_list(@PathVariable("restaurant_id") int restaurant_id) {
        List<ReviewResponseDto> reviewResponseDtoList = reviewService.review_list(restaurant_id);
        return ResponseEntity.ok().body(reviewResponseDtoList);
    }
}
