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
import java.util.stream.Collectors;

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
                    .menuStatus(menu.getMenuStatus())
                    .price(menu.getPrice())
                    .build();
        } else {
            throw new RuntimeException("Menu not found");
        }
    }

    /*
    @Transactional
    public MenuDTO createMenu(MenuDTO menuDTO) {
        Optional<Restaurant> restaurantEntityOptional = restaurantRepository.findById(menuDTO.getRestaurantId());
        Restaurant restaurant;

        if(restaurantEntityOptional.isPresent()){
            restaurant = restaurantEntityOptional.get();

            Menu menu = Menu.builder()
                    .menuName(menuDTO.getMenuName())
                    .menuBody(menuDTO.getMenuBody())
                    .menuStatus(menuDTO.isMenuStatus())
                    .price(menuDTO.getPrice())
                    .restaurant(restaurant)
                    .build();

            menuRepository.save(menu);
            restaurantRepository.save(restaurant);

            return MenuDTO.toEntity(menu);
        }
        return null;
    }

     */
    @Transactional
    public MenuDTO createMenu(MenuDTO menuDTO, Long presidentId) {
        // `presidentId`로 첫 번째 `Restaurant` 조회
        Optional<Restaurant> restaurantEntityOptional = restaurantRepository.findFirstByPresidentId(presidentId);

        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = restaurantEntityOptional.get();

            // `Menu` 생성 및 `Restaurant` 연관 설정
            Menu menu = Menu.builder()
                    .menuName(menuDTO.getMenuName())
                    .menuBody(menuDTO.getMenuBody())
                    .menuStatus(menuDTO.isMenuStatus())
                    .price(menuDTO.getPrice())
                    .restaurant(restaurant)
                    .build();

            menuRepository.save(menu);

            return MenuDTO.toEntity(menu);
        }
        return null; // `Restaurant`을 찾지 못한 경우 `null` 반환
    }
    @Transactional
    public MenuDTO updateMenu(MenuDTO menuDTO) {
        Optional<Menu> menuEntityOptional = menuRepository.findById(menuDTO.getId());
        if(menuEntityOptional.isPresent()){
            Menu menu = menuEntityOptional.get();
            menu.updateMenu(menuDTO.getMenuName(), menuDTO.getMenuBody(), menuDTO.isMenuStatus(), menuDTO.getPrice());

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

    public List<MenuDTO> getMenusByPresidentId(Long presidentId) {
        List<Menu> menus = menuRepository.findByPresidentId(presidentId);
        System.out.println(menus);
        return menus.stream().map(menu -> new MenuDTO(
                menu.getId(),
                menu.getMenuName(),
                menu.getMenuBody(),
                menu.getPrice(),
                menu.getMenuStatus()
        ))
                .collect(Collectors.toList());
    }

    /*

            Optional<Menu> menuOptional = menuRepository.findById(menuId);
        if (menuOptional.isPresent()) {
            Menu menu = menuOptional.get();
            return MenuDTO.builder()
                    .id(menu.getId())
                    .menuName(menu.getMenuName())
                    .menuBody(menu.getMenuBody())
                    .menuStatus(menu.getMenuStatus())
                    .price(menu.getPrice())
                    .build();
        } else {
            throw new RuntimeException("Menu not found");
        }
        public List<RestaurantDTO> findRestaurantsByPresidentId(Long presidentId) {
        List<Restaurant> restaurants = restaurantRepository.findByPresidentId(presidentId);
        List<RestaurantDTO> restaurantDTO = new ArrayList<>();

        for(Restaurant restaurant : restaurants){
            restaurantDTO.add(RestaurantDTO.toEntity(restaurant));
        }

        return restaurantDTO;
    }
     */
}
