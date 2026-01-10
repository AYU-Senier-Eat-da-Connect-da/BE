package com.eatda.domain.user.president.controller;

import com.eatda.domain.menu.dto.MenuDTO;
import com.eatda.domain.menu.service.MenuService;
import com.eatda.domain.user.president.dto.PresidentDTO;
import com.eatda.domain.user.president.service.PresidentService;
import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/president")
public class PresidentController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final PresidentService presidentService;

    @GetMapping("/findMyRestaurant/{presidentId}")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantByPresidentId(@PathVariable Long presidentId) {
        List<RestaurantDTO> restaurants = restaurantService.findRestaurantsByPresidentId(presidentId);
        if (restaurants != null) {
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findMyMenu/{presidentId}")
    public ResponseEntity<List<MenuDTO>> getMenusByPresidentId(@PathVariable Long presidentId) {
        List<MenuDTO> menus = menuService.getMenusByPresidentId(presidentId);
        if (menus != null) {
            return new ResponseEntity<>(menus, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get/{presidentId}")
    public ResponseEntity<PresidentDTO> getMyInformation(@PathVariable Long presidentId) {
        PresidentDTO presidentDTO = presidentService.getPresidentInfo(presidentId);
        if (presidentDTO != null) {
            return new ResponseEntity<>(presidentDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
