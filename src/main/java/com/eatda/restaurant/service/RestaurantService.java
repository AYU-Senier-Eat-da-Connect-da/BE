package com.eatda.restaurant.service;

import com.eatda.menu.domain.Menu;
import com.eatda.menu.domain.MenuDTO;
import com.eatda.menu.repository.MenuRepository;
import com.eatda.president.domain.President;
import com.eatda.president.repository.PresidentRepository;
import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.domain.RestaurantDTO;
import com.eatda.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final PresidentRepository presidentRepository;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    public RestaurantDTO getRestaurantById(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if (restaurantOptional.isPresent()) {
            Restaurant restaurantEntity = restaurantOptional.get();

            List<Menu> menus = menuRepository.findByRestaurantId(restaurantEntity.getId());

            List<MenuDTO> menuDTOS = new ArrayList<>();
            for (Menu menu : menus) {
                MenuDTO menuDTO = new MenuDTO();
                menuDTO.setId(menu.getId());
                menuDTO.setMenuName(menu.getMenuName());
                menuDTO.setMenuBody(menu.getMenuBody());
                menuDTO.setPrice(menu.getPrice());
                menuDTO.setMenuStatus(menu.getMenuStatus());
                menuDTOS.add(menuDTO);
            }

            return RestaurantDTO.builder()
                    .id(restaurantEntity.getId())
                    .restaurantName(restaurantEntity.getRestaurantName())
                    .restaurantAddress(restaurantEntity.getRestaurantAddress())
                    .restaurantNumber(restaurantEntity.getRestaurantNumber())
                    .restaurantBody(restaurantEntity.getRestaurantBody())
                    .restaurantCategory(restaurantEntity.getRestaurantCategory())
                    .presidentId(restaurantEntity.getPresident().getId())
                    .menus(menuDTOS)
                    .build();
        } else {
            throw new RuntimeException("Restaurant not found");
        }
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
        Optional<Restaurant> restaurantEntityOptional = restaurantRepository.findById(restaurantId);
        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = restaurantEntityOptional.get();
            restaurant.updateRestaurant(
                    restaurantDTO.getRestaurantName(),
                    restaurantDTO.getRestaurantAddress(),
                    restaurantDTO.getRestaurantNumber(),
                    restaurantDTO.getRestaurantBody(),
                    restaurantDTO.getRestaurantCategory()
            );

            restaurantRepository.save(restaurant);

            return RestaurantDTO.toEntity(restaurant);
        } else {
            throw new RuntimeException("Restaurant not found");
        }
    }

    @Transactional
    public void deleteRestaurant(Long restaurantId) {
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(restaurantId);
        if(restaurantOptional.isPresent()){
            Restaurant restaurantEntity = restaurantOptional.get();
            restaurantRepository.delete(restaurantEntity);
        }else{
            throw new RuntimeException("Restaurant not found");
        }
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