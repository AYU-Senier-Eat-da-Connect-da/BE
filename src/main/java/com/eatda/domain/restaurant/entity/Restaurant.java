package com.eatda.domain.restaurant.entity;

import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.user.president.entity.President;
import com.eatda.domain.review.entity.Review;

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
    @Column(columnDefinition = "TEXT")
    private String restaurantBody;
    private String restaurantCategory;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="president_id")

    private President president;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)

    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;

    public void updateRestaurant(String restaurantName, String restaurantAddress, String restaurantNumber, String restaurantBody, String restaurantCategory) {
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantNumber = restaurantNumber;
        this.restaurantBody = restaurantBody;
        this.restaurantCategory = restaurantCategory;
    }
}
