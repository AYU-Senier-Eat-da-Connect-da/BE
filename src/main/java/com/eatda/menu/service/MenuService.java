package com.eatda.menu.service;

import com.eatda.menu.domain.Menu;
import com.eatda.menu.domain.MenuDTO;
import com.eatda.menu.repository.MenuRepository;
import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuDTO getMenuById(Long menuId) {
        Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            return MenuDTO.builder()
                    .id(menu.getId())
                    .menuName(menu.getMenuName())
                    .menuBody(menu.getMenuBody())
                    .price(menu.getPrice())
                    .stockQuantity(menu.getStockQuantity())
                    .build();
        } else {
            throw new RuntimeException("Menu not found");
        }
    }


    @Transactional
    public MenuDTO createMenu(MenuDTO menuDTO) {
        Optional<Restaurant> restaurantEntityOptional = restaurantRepository.findById(menuDTO.getRestaurantId());
        Restaurant restaurant;

        if(restaurantEntityOptional.isPresent()){
            restaurant = restaurantEntityOptional.get();

            Menu menu = Menu.builder()
                    .menuName(menuDTO.getMenuName())
                    .menuBody(menuDTO.getMenuBody())
                    .price(menuDTO.getPrice())
                    .stockQuantity(menuDTO.getStockQuantity())
                    .restaurant(restaurant)
                    .build();

            menuRepository.save(menu);
            restaurantRepository.save(restaurant);

            return MenuDTO.toEntity(menu);
        }
        return null;
    }

    @Transactional
    public MenuDTO updateMenu(MenuDTO menuDTO) {
        Optional<Menu> menuEntityOptional = menuRepository.findById(menuDTO.getId());
        if(menuEntityOptional.isPresent()){
            Menu menu = menuEntityOptional.get();
            menu.updateMenu(menuDTO.getMenuName(), menuDTO.getMenuBody(), menuDTO.getStockQuantity(), menu.getPrice());

            menuRepository.save(menu);

            return MenuDTO.toEntity(menu);
        }else{
            throw new RuntimeException("menu not found");
        }
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Optional<Menu> menuEntityOptional = menuRepository.findById(menuId);
        if(menuEntityOptional.isPresent()){
            Menu menuEntity = menuEntityOptional.get();
            menuRepository.delete(menuEntity);
        } else {
            throw new RuntimeException("Menu not found");
        }
    }
}
