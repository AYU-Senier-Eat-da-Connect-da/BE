package com.eatda.review.domain;

import com.eatda.child.domain.ChildEntity;
import com.eatda.restaurant.domain.RestaurantEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Builder
public class ReviewEntity {

    @Id @GeneratedValue
    @Column(name = "review_id")
    private int review_id;

    private int review_star;

    private String review_body;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "child_id")
    private ChildEntity child;
}
