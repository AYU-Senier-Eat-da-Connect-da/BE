package com.eatda.restaurant.controller;

import com.eatda.restaurant.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant/search/")
public class SearchController {

    private final SearchService searchService;

    // 가게 전체 리스트 조회
    @GetMapping("/restaurants")
    public Map<String, Object> getAllRestaurants() {
        return searchService.getAllRestaurants();
    }

    // 가게 단일(상세) 조회
    @GetMapping("/restaurants/{restaurantId}")
    public Map<String, Object> getRestaurantById(@PathVariable Long restaurantId) {
        return searchService.getRestaurantById(restaurantId);
    }

    // 가게 메뉴 이름으로 검색
    @GetMapping("/menu")
    public Map<String, Object> searchByMenuName(@RequestParam String menuName) {
        return searchService.searchByMenuName(menuName);
    }
}
