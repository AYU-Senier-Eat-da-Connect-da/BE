package com.eatda.review.service;

import com.eatda.review.domain.ReviewRequestDto;
import com.eatda.review.domain.ReviewEntity;
import com.eatda.review.domain.ReviewResponseDto;
import com.eatda.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    //리뷰 등록
    @Transactional
    public boolean review_write(ReviewRequestDto reviewRequestDto) {
        try {
            ReviewEntity review = reviewRequestDto.toEntity();
            reviewRepository.save(review);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //리뷰 전체 조회
    public List<ReviewResponseDto> review_list(int restaurant_id) {
        List<ReviewEntity> reviews = reviewRepository.findByRestaurantId(restaurant_id);
        return reviews.stream()
                .map(ReviewResponseDto::new) // ReviewEntity를 ReviewResponseDto로 변환
                .collect(Collectors.toList()); // 리스트로 수집
    }
}
