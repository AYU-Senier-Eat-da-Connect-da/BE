package com.eatda.restaurant.service;

import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.domain.RestaurantDTO;
import com.eatda.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final RestaurantRepository restaurantRepository;

    // 가게 전체 리스트 조회 서비스
    public Map<String, Object> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        Map<String, Object> result = new HashMap<>();
        result.put("restaurants", restaurants);
        return result;
    }

    // 가게 단일(상세) 조회 서비스
    public Map<String, Object> getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));
        Map<String, Object> result = new HashMap<>();
        result.put("restaurant", restaurant);
        return result;
    }

    // 가게 메뉴 이름으로 검색 서비스
    public Map<String, Object> searchByMenuName(String menuName) {
        List<Restaurant> restaurants = restaurantRepository.findByMenuNameContaining(menuName);
        Map<String, Object> result = new HashMap<>();
        if (restaurants.isEmpty()) {
            result.put("message", "해당 메뉴를 포함하는 가게가 없습니다.");
        } else {
            result.put("restaurants", restaurants);
        }
        return result;
    }

    public List<RestaurantDTO> searchByText(String searchText) {
        return restaurantRepository.findByTextContaining(searchText);
    }

    public List<RestaurantDTO> getRestaurantsByCategory(String category) {
        List<Restaurant> restaurants = restaurantRepository.findByRestaurantCategory(category);
        return restaurants.stream()
                .map(RestaurantDTO::new)
                .collect(Collectors.toList());
    }
}
