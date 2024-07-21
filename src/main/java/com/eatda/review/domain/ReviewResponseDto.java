package com.eatda.review.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReviewResponseDto {
    private int review_id;
    private String order_number;
    private int review_star;
    private String review_body;

    private int restaurant_id;
    private int child_id;

    //Todo: 사진 추가해야함.

    /*
        Entity -> DTO
     */
    public ReviewResponseDto(ReviewEntity review) {
        this.review_id = review.getReview_id();
        this.review_star = review.getReview_star();
        this.review_body = review.getReview_body();
        this.restaurant_id = review.getRestaurant().getRestaurant_id();
        this.child_id = review.getChild().getChild_id();
    }
}
