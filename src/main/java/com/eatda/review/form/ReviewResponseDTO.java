package com.eatda.review.form;

import com.eatda.review.domain.Review;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {

    private Long id;
    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private int review_star;

    private String review_body; // 리뷰 내용

    private LocalDateTime createdAt;

    private Long childId;

    private Long restaurantId;

    public static ReviewResponseDTO toEntity(Review review) {
        return ReviewResponseDTO.builder()
                .id(review.getId())
                .review_star(review.getReview_star())
                .review_body(review.getReview_body())
                .createdAt(review.getCreatedAt())
                .childId(review.getChild().getId())
                .restaurantId(review.getRestaurant().getId())
                .build();
    }
}
