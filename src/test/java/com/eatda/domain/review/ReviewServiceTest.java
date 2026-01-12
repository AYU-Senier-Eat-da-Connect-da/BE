package com.eatda.domain.review;

import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import com.eatda.domain.review.dto.ReviewResponseDTO;
import com.eatda.domain.review.entity.Review;
import com.eatda.domain.review.repository.ReviewRepository;
import com.eatda.domain.review.service.ReviewService;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired private ReviewService reviewService;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ChildRepository childRepository;
    @Autowired private RestaurantRepository restaurantRepository;
    @Autowired private EntityManager em;

    private Child testChild;
    private Restaurant testRestaurant1;
    private Restaurant testRestaurant2;
    private Restaurant testRestaurant3;
    private Review review1;
    private Review review2;
    private Review review3;

    @BeforeEach
    void setUp() {
        // 테스트용 아동
        testChild = Child.builder()
                .childName("신짱구")
                .build();
        childRepository.save(testChild);

        // 테스트용 가게 3개 (N+1 문제 재현을 위해 여러 개)
        testRestaurant1 = Restaurant.builder()
                .restaurantName("건강밥상 심마니 평촌")
                .build();
        testRestaurant2 = Restaurant.builder()
                .restaurantName("맛있는 순대국")
                .build();
        testRestaurant3 = Restaurant.builder()
                .restaurantName("행복한 김밥")
                .build();
        restaurantRepository.save(testRestaurant1);
        restaurantRepository.save(testRestaurant2);
        restaurantRepository.save(testRestaurant3);

        review1 = Review.builder()
                .review_star(5)
                .review_body("정말 맛있어요!")
                .child(testChild)
                .restaurant(testRestaurant1)
                .createdAt(LocalDateTime.now())
                .build();

       review2 = Review.builder()
                .review_star(4)
                .review_body("순대국 맛집!")
                .child(testChild)
                .restaurant(testRestaurant2)
                .createdAt(LocalDateTime.now())
                .build();

       review3 = Review.builder()
                .review_star(3)
                .review_body("괜찮아요")
                .child(testChild)
                .restaurant(testRestaurant3)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review1);
        reviewRepository.save(review2);
        reviewRepository.save(review3);

        em.flush();
        em.clear();
    }

    @Test
    @DisplayName("아동 ID로 리뷰 조회 - N+1 문제 발생")
    void findReviewByChildId_N_Plus_1_Problem() {
        System.out.println("============ 쿼리 시작 ============");

        // When: 아동의 리뷰 목록 조회
        // 예상 쿼리:
        // 1) SELECT * FROM review WHERE child_id = ? (리뷰 3개 조회)
        // 2) SELECT * FROM restaurant WHERE id = ? (식당 1 조회) - N+1
        // 3) SELECT * FROM restaurant WHERE id = ? (식당 2 조회) - N+1
        // 4) SELECT * FROM restaurant WHERE id = ? (식당 3 조회) - N+1
        // 총 4번의 쿼리 발생 (1 + N, N=3)

        List<ReviewResponseDTO> reviews = reviewService.getReviewLIstByChildId(testChild.getId());

        System.out.println("============ 쿼리 끝 ============");

        // Then: 리뷰 3개가 정상 조회되어야 함
        assertEquals(3, reviews.size());

        // 각 리뷰의 식당 정보가 정상적으로 매핑되었는지 확인
        for (ReviewResponseDTO review : reviews) {
            assertNotNull(review.getRestaurantId());
            System.out.println("리뷰 ID: " + review.getId() +
                    ", 식당 ID: " + review.getRestaurantId() +
                    ", 별점: " + review.getReview_star());
        }
    }

    @Test
    @DisplayName("식당 ID로 리뷰 조회 - N+1 문제 발생")
    void findReviewByRestaurantId_N_Plus_1_Problem() {
        // Given: 하나의 식당에 여러 아동이 리뷰 작성
        Child child2 = Child.builder().childName("철수").build();
        Child child3 = Child.builder().childName("훈이").build();
        childRepository.save(child2);
        childRepository.save(child3);

        Review review2 = Review.builder()
                .review_star(4)
                .review_body("철수도 맛있대요")
                .child(child2)
                .restaurant(testRestaurant1)
                .createdAt(LocalDateTime.now())
                .build();

        Review review3 = Review.builder()
                .review_star(5)
                .review_body("훈이도 최고래요")
                .child(child3)
                .restaurant(testRestaurant1)
                .createdAt(LocalDateTime.now())
                .build();

        reviewRepository.save(review2);
        reviewRepository.save(review3);

        em.flush();
        em.clear();

        System.out.println("============ 쿼리 시작 ============");

        // When: 식당의 리뷰 목록 조회
        // 예상 쿼리:
        // 1) SELECT * FROM review WHERE restaurant_id = ? (리뷰 3개 조회)
        // 2) SELECT * FROM child WHERE id = ? (아동 1 조회) - N+1
        // 3) SELECT * FROM child WHERE id = ? (아동 2 조회) - N+1
        // 4) SELECT * FROM child WHERE id = ? (아동 3 조회) - N+1
        // 총 4번의 쿼리 발생 (1 + N, N=3)

        List<ReviewResponseDTO> reviews = reviewService.getReviewListByRestaurantId(testRestaurant1.getId());

        System.out.println("============ 쿼리 끝 ============");

        // Then
        assertEquals(3, reviews.size()); // 짱구 + 철수 + 훈이의 리뷰

        for (ReviewResponseDTO review : reviews) {
            assertNotNull(review.getChildId());
            System.out.println("리뷰 ID: " + review.getId() +
                    ", 아동 ID: " + review.getChildId() +
                    ", 별점: " + review.getReview_star());
        }
    }
}