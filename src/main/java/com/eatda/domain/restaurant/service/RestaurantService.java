package com.eatda.domain.restaurant.service;

import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.menu.repository.MenuRepository;
import com.eatda.domain.user.president.entity.President;
import com.eatda.domain.user.president.repository.PresidentRepository;
import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.entity.Restaurant;
import com.eatda.domain.restaurant.repository.RestaurantRepository;
import com.eatda.global.exception.CustomException;
import com.eatda.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final PresidentRepository presidentRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        List<Menu> menus = menuRepository.findByRestaurantId(restaurant.getId());

        return RestaurantDTO.fromWithMenus(restaurant, menus);
    }

    @Transactional
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        President president = presidentRepository.findById(restaurantDTO.getPresidentId())
                .orElseThrow(() -> new CustomException(ErrorCode.PRESIDENT_NOT_FOUND));

        Restaurant restaurant = Restaurant.builder()
                .restaurantName(restaurantDTO.getRestaurantName())
                .restaurantAddress(restaurantDTO.getRestaurantAddress())
                .restaurantNumber(restaurantDTO.getRestaurantNumber())
                .restaurantBody(restaurantDTO.getRestaurantBody())
                .restaurantCategory(restaurantDTO.getRestaurantCategory())
                .president(president)
                .build();

        restaurantRepository.save(restaurant);
        presidentRepository.save(president);

        return RestaurantDTO.from(restaurant);
    }

    @Transactional
    public RestaurantDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        restaurant.updateInfo(
                restaurantDTO.getRestaurantName(),
                restaurantDTO.getRestaurantAddress(),
                restaurantDTO.getRestaurantNumber(),
                restaurantDTO.getRestaurantBody(),
                restaurantDTO.getRestaurantCategory()
        );

        return RestaurantDTO.from(restaurant);
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
        restaurantRepository.delete(restaurant);
    }

    public List<RestaurantDTO> findRestaurantsByPresidentId(Long presidentId) {
        List<Restaurant> restaurants = restaurantRepository.findByPresidentId(presidentId);
        return RestaurantDTO.from(restaurants);
    }
}
