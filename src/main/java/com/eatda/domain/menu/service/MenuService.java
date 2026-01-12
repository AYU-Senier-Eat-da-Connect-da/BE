package com.eatda.domain.menu.service;

import com.eatda.domain.menu.dto.MenuDTO;
import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.menu.repository.MenuRepository;
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
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuDTO getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
        return MenuDTO.from(menu);
    }

    @Transactional
    public MenuDTO createMenu(MenuDTO menuDTO, Long presidentId) {
        Restaurant restaurant = restaurantRepository.findFirstByPresidentId(presidentId)
                .orElseThrow(() -> new CustomException(ErrorCode.RESTAURANT_NOT_FOUND));

        Menu menu = Menu.builder()
                .menuName(menuDTO.getMenuName())
                .menuBody(menuDTO.getMenuBody())
                .menuStatus(menuDTO.isMenuStatus())
                .price(menuDTO.getPrice())
                .restaurant(restaurant)
                .build();

        menuRepository.save(menu);

        return MenuDTO.from(menu);
    }

    @Transactional
    public MenuDTO updateMenu(MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        menu.updateInfo(menuDTO.getMenuName(), menuDTO.getMenuBody(), menuDTO.isMenuStatus(), menuDTO.getPrice());

        return MenuDTO.from(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
        menuRepository.delete(menu);
    }

    public List<MenuDTO> getMenusByPresidentId(Long presidentId) {
        List<Menu> menus = menuRepository.findByPresidentId(presidentId);
        return MenuDTO.from(menus);
    }
}
