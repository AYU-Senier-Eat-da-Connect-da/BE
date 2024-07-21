package com.eatda.restaurant.domain;

import com.eatda.review.domain.ReviewEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class RestaurantEntity {

    @Id @GeneratedValue
    @Column(name = "restaurant_id")
    private int restaurant_id;

    private String restaurant_name;

    private String restaurant_address;

    private String restaurant_number;

    private String restaurant_body;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;
}
