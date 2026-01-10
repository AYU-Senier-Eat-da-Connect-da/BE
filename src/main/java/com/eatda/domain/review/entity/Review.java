package com.eatda.domain.review.entity;

import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.restaurant.entity.Restaurant;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private String review_body;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private Child child;
    // ===== 도메인 비즈니스 로직 =====

    /**
     * 리뷰 내용을 수정합니다.
     */
    public void updateContent(String newBody) {
        this.review_body = newBody;
    }

    /**
     * 별점을 수정합니다.
     */
    public void updateStar(int newStar) {
        if (newStar < 1 || newStar > 5) {
            throw new IllegalArgumentException("별점은 1~5 사이여야 합니다.");
        }
        this.review_star = newStar;
    }

    /**
     * 리뷰 전체를 수정합니다.
     */
    public void update(int star, String body) {
        updateStar(star);
        updateContent(body);
    }

    /**
     * 해당 식당의 리뷰인지 확인합니다.
     */
    public boolean belongsTo(Restaurant restaurant) {
        return this.restaurant != null && this.restaurant.getId().equals(restaurant.getId());
    }

    /**
     * 해당 아동이 작성한 리뷰인지 확인합니다.
     */
    public boolean isWrittenBy(Child child) {
        return this.child != null && this.child.getId().equals(child.getId());
    }
}

