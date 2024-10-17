package com.eatda.review.domain;

import com.eatda.child.domain.Child;
import com.eatda.picture.domain.Picture;
import com.eatda.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Review {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Min(value = 1, message = "별점은 최소 1점이어야 합니다.")
    @Max(value = 5, message = "별점은 최대 5점이어야 합니다.")
    private int review_star;

    private String review_body; //리뷰 내용
    private LocalDateTime createdAt; // 작성 시간


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;

    @OneToMany(mappedBy = "review")
    private List<Picture> pictures;
}
