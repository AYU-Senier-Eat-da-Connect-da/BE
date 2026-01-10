package com.eatda.domain.restaurant.service;

import com.eatda.domain.menu.dto.MenuDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

        List<MenuDTO> menuDTOS = menus.stream()
                .map(menu -> MenuDTO.builder()
                        .id(menu.getId())
                        .menuName(menu.getMenuName())
                        .menuBody(menu.getMenuBody())
                        .price(menu.getPrice())
                        .menuStatus(menu.getMenuStatus())
                        .build())
                .collect(Collectors.toList());

        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantAddress(restaurant.getRestaurantAddress())
                .restaurantNumber(restaurant.getRestaurantNumber())
                .restaurantBody(restaurant.getRestaurantBody())
                .restaurantCategory(restaurant.getRestaurantCategory())
                .presidentId(restaurant.getPresident().getId())
                .menus(menuDTOS)
                .build();
    }

    @Transactional
    public RestaurantDTO createRestaurant(RestaurantDTO restaurantDTO) {
        Optional<President> presidentEntityOptional = presidentRepository.findById(restaurantDTO.getPresidentId());
        if(presidentEntityOptional.isPresent()){
            President president = presidentEntityOptional.get();

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

            return RestaurantDTO.toEntity(restaurant);
        }
        return null;
    }

    @Transactional
    public RestaurantDTO updateRestaurant(Long restaurantId, RestaurantDTO restaurantDTO) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        // DDD: 도메인 메서드를 통한 정보 수정
        restaurant.updateInfo(
                restaurantDTO.getRestaurantName(),
                restaurantDTO.getRestaurantAddress(),
                restaurantDTO.getRestaurantNumber(),
                restaurantDTO.getRestaurantBody(),
                restaurantDTO.getRestaurantCategory()
        );

        return RestaurantDTO.toEntity(restaurant);
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));
        restaurantRepository.delete(restaurant);
    }

    public List<RestaurantDTO> findRestaurantsByPresidentId(Long presidentId) {
        List<Restaurant> restaurants = restaurantRepository.findByPresidentId(presidentId);
        List<RestaurantDTO> restaurantDTO = new ArrayList<>();

        for(Restaurant restaurant : restaurants){
            restaurantDTO.add(RestaurantDTO.toEntity(restaurant));
        }

        return restaurantDTO;
    }
}
