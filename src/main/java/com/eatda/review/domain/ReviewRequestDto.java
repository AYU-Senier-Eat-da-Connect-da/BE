package com.eatda.review.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewRequestDto {

    private int review_id;
    private String order_number;
    private String review_star;
    private String review_body;

    private int restaurant_id;
    private int child_id;

    //Todo: 사진 추가해야함.

    public ReviewEntity toEntity() {
        ReviewEntity review = ReviewEntity.builder()
                .review_id(review_id)
                .review_body(review_body)
                .child(child)
                .restaurant(restaurant)
                .build();
        return review;
    }
}
