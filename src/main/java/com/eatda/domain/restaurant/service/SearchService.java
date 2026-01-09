package com.eatda.domain.restaurant.service;

import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchService {

    private final RestaurantRepository restaurantRepository;

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantRepository.findAll().stream()
                .map(RestaurantDTO::new)
                .collect(Collectors.toList());
    }

    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new IllegalArgumentException("해당 가게를 찾을 수 없습니다."));
        return new RestaurantDTO(restaurant);
    }

    public List<RestaurantDTO> searchByMenuName(String menuName) {
        return restaurantRepository.findByMenuNameContaining(menuName).stream()
                .map(RestaurantDTO::new)
                .collect(Collectors.toList());
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

