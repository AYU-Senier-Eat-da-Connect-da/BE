package com.eatda.president.controller;

import com.eatda.menu.domain.MenuDTO;
import com.eatda.menu.service.MenuService;
import com.eatda.president.service.PresidentService;
import com.eatda.restaurant.domain.RestaurantDTO;
import com.eatda.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/president")
public class PresidentController {

    private final RestaurantService restaurantService;
    private final MenuService menuService;

    // 사장님 id값으로 내 가게 찾기
    @GetMapping("/findMyRestaurant/{presidentId}")
    public ResponseEntity<List<RestaurantDTO>> getRestaurantByPresidentId(@PathVariable Long presidentId){
        List<RestaurantDTO> restaurants = restaurantService.findRestaurantsByPresidentId(presidentId);
        if(restaurants!=null){
            return new ResponseEntity<>(restaurants, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 사장님 id값으로 내 가게 찾기
    @GetMapping("/findMyMenu/{presidentId}")
    public ResponseEntity<List<MenuDTO>> getMenusByPresidentId(@PathVariable Long presidentId) {
        List<MenuDTO> menus = menuService.getMenusByPresidentId(presidentId);
        if(menus!=null){
            return new ResponseEntity<>(menus, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
