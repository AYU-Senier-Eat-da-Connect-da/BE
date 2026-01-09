package com.eatda.domain.restaurant.entity;

import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.user.president.entity.President;
import com.eatda.domain.review.entity.Review;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="president_id")
    private President president;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Menu> menus = new ArrayList<>();

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Builder.Default
    private List<Review> reviews = new ArrayList<>();

    // ===== 도메인 비즈니스 로직 =====

    /**
     * 식당 정보를 수정합니다.
     */
    public void updateInfo(String name, String address, String number, String body, String category) {
        if (name != null && !name.isBlank()) {
            this.restaurantName = name;
        }
        if (address != null && !address.isBlank()) {
            this.restaurantAddress = address;
        }
        if (number != null) {
            this.restaurantNumber = number;
        }
        if (body != null) {
            this.restaurantBody = body;
        }
        if (category != null) {
            this.restaurantCategory = category;
        }
    }

    /**
     * 식당 이름을 변경합니다.
     */
    public void changeName(String newName) {
        if (newName == null || newName.isBlank()) {
            throw new IllegalArgumentException("식당 이름은 비어있을 수 없습니다.");
        }
        this.restaurantName = newName;
    }

    /**
     * 식당 카테고리를 변경합니다.
     */
    public void changeCategory(String newCategory) {
        this.restaurantCategory = newCategory;
    }

    /**
     * 메뉴 개수를 반환합니다.
     */
    public int getMenuCount() {
        return this.menus != null ? this.menus.size() : 0;
    }

    /**
     * 리뷰 개수를 반환합니다.
     */
    public int getReviewCount() {
        return this.reviews != null ? this.reviews.size() : 0;
    }

    /**
     * 평균 별점을 계산합니다.
     */
    public double getAverageRating() {
        if (this.reviews == null || this.reviews.isEmpty()) {
            return 0.0;
        }
        return this.reviews.stream()
                .mapToInt(Review::getReview_star)
                .average()
                .orElse(0.0);
    }
}

