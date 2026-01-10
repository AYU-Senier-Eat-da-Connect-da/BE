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
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuDTO getMenuById(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        return MenuDTO.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .menuBody(menu.getMenuBody())
                .menuStatus(menu.getMenuStatus())
                .price(menu.getPrice())
                .build();
    }

    @Transactional
    public MenuDTO createMenu(MenuDTO menuDTO, Long presidentId) {
        Optional<Restaurant> restaurantEntityOptional = restaurantRepository.findFirstByPresidentId(presidentId);

        if (restaurantEntityOptional.isPresent()) {
            Restaurant restaurant = restaurantEntityOptional.get();

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
        return null;
    }

    @Transactional
    public MenuDTO updateMenu(MenuDTO menuDTO) {
        Menu menu = menuRepository.findById(menuDTO.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));

        // DDD: 도메인 메서드를 통한 정보 수정
        menu.updateInfo(menuDTO.getMenuName(), menuDTO.getMenuBody(), menuDTO.isMenuStatus(), menuDTO.getPrice());

        return MenuDTO.toEntity(menu);
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Menu menu = menuRepository.findById(menuId)
                .orElseThrow(() -> new CustomException(ErrorCode.MENU_NOT_FOUND));
        menuRepository.delete(menu);
    }

    public List<MenuDTO> getMenusByPresidentId(Long presidentId) {
        List<Menu> menus = menuRepository.findByPresidentId(presidentId);
        return menus.stream().map(menu -> new MenuDTO(
                menu.getId(),
                menu.getMenuName(),
                menu.getMenuBody(),
                menu.getPrice(),
                menu.getMenuStatus()
        )).collect(Collectors.toList());
    }
}
