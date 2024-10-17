package com.eatda.review.form;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewRequestDTO {

    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private int review_star;

    private String review_body; //리뷰 내용

}
