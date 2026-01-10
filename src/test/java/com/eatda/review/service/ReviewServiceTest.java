package com.eatda.review.service;

import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import com.eatda.domain.review.repository.ReviewRepository;
import com.eatda.domain.review.service.ReviewService;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ReviewServiceTest {

    @Autowired private ReviewService reviewService;
    @Autowired private ReviewRepository reviewRepository;
    @Autowired private ChildRepository childRepository;
    @Autowired private RestaurantRepository restaurantRepository;

    private Child testChild;
    private Restaurant testRestaurant;

    @BeforeEach
    void setUp() {
        //테스트용 아동
        testChild = Child.builder()
                .childName("신짱구")
                .build();
        childRepository.save(testChild);

        //테스트용 가게
        testRestaurant = Restaurant.builder()
                .restaurantName("건강밥상 심마니 평촌")
                .build();
        restaurantRepository.save(testRestaurant);
    }


    @Test
    @DisplayName("리뷰가 정상 등록되면 성공")
    void createReview() throws Exception {
        //Given
        ReviewRequestDTO reviewRequestDTO = ReviewRequestDTO.builder()
                .review_star(5)
                .review_body("건강한 한끼를 먹을 수 있어서 좋았어요 추천합니다!")
                .build();
        //When
        ReviewResponseDTO reviewResponseDTO = reviewService.createReview(
                testRestaurant.getId(),
                reviewRequestDTO,
                testChild.getId()
        );

        //Then
        assertNotNull(reviewResponseDTO);
        assertNotNull(reviewResponseDTO.getId());
        assertEquals(5, reviewResponseDTO.getReview_star());
        assertEquals("건강한 한끼를 먹을 수 있어서 좋았어요 추천합니다!", reviewResponseDTO.getReview_body());
        assertEquals(testChild.getId(), reviewResponseDTO.getChildId());
        assertEquals(testRestaurant.getId(), reviewResponseDTO.getRestaurantId());

        // DB 저장 확인
        Review addedReview = reviewRepository.findById(reviewResponseDTO.getId())
                .orElse(null);
        assertNotNull(addedReview);
        assertEquals(5, addedReview.getReview_star());
        assertEquals("건강한 한끼를 먹을 수 있어서 좋았어요 추천합니다!",addedReview.getReview_body());
        assertEquals(testChild.getId(), addedReview.getChild().getId());
        assertEquals(testRestaurant.getId(), addedReview.getRestaurant().getId());
    }

    @Test
    void getReviewById() {

    }

    @Test
    void getReviewListByRestaurantId() {
    }
}