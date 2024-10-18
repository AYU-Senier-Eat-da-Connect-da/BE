package com.eatda.restaurant.domain;


import com.eatda.menu.domain.Menu;
import com.eatda.president.domain.President;
import com.eatda.review.domain.Review;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String restaurantName;
    private String restaurantAddress;
    private String restaurantNumber;
    private String restaurantBody;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="president_id")
    @JsonBackReference  // President의 restaurant 필드와 순환 참조를 방지
    private President president;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference // Menu와의 관계에서 관리되는 참조로 표시
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    public void updateRestaurant(String restaurantName, String restaurantAddress, String restaurantNumber, String restaurantBody) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantNumber = restaurantNumber;
        this.restaurantBody = restaurantBody;
    }
}
