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
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }
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
        Optional<Menu> menuEntityOptional = menuRepository.findById(menuDTO.getId());
        if (menuEntityOptional.isPresent()) {
            Menu menu = menuEntityOptional.get();
            menu.updateMenu(menuDTO.getMenuName(), menuDTO.getMenuBody(), menuDTO.isMenuStatus(), menuDTO.getPrice());
            menuRepository.save(menu);
            return MenuDTO.toEntity(menu);
        } else {
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }
    }

    @Transactional
    public void deleteMenu(Long menuId) {
        Optional<Menu> menuEntityOptional = menuRepository.findById(menuId);
        if (menuEntityOptional.isPresent()) {
            Menu menuEntity = menuEntityOptional.get();
            menuRepository.delete(menuEntity);
        } else {
            throw new CustomException(ErrorCode.MENU_NOT_FOUND);
        }
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
