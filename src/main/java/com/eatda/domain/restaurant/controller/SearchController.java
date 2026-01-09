package com.eatda.domain.restaurant.controller;

import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/search")
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/restaurants")
    public ResponseEntity<List<RestaurantDTO>> getAllRestaurants() {
        return ResponseEntity.ok(searchService.getAllRestaurants());
    }

    @GetMapping("/restaurants/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurant(@PathVariable Long restaurantId) {
        return ResponseEntity.ok(searchService.getRestaurantById(restaurantId));
    }

    @GetMapping("/menu")
    public ResponseEntity<List<RestaurantDTO>> searchByMenuName(@RequestParam String menuName) {
        return ResponseEntity.ok(searchService.searchByMenuName(menuName));
    }

    @GetMapping("/text")
    public ResponseEntity<List<RestaurantDTO>> searchByText(@RequestParam String text) {
        return ResponseEntity.ok(searchService.searchByText(text));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<RestaurantDTO>> searchByCategory(@PathVariable String category) {
        return ResponseEntity.ok(searchService.getRestaurantsByCategory(category));
    }
}

