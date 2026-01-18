package com.eatda.domain.restaurant.controller;

import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.dto.RestaurantSearchResult;
import com.eatda.domain.restaurant.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
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

    /**
     * V1: 기존 LIKE 검색 (성능 측정용)
     */
    @GetMapping("/text")
    public ResponseEntity<Page<RestaurantDTO>> searchByText(
            @RequestParam String text,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(searchService.searchByText(text, pageable));
    }

    /**
     * V2: Full-Text Index + Slice 적용 (최적화 버전)
     */
    @GetMapping("/fulltext")
    public ResponseEntity<Slice<RestaurantSearchResult>> searchByFullText(
            @RequestParam String text,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(searchService.searchByFullText(text, pageable));
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<List<RestaurantDTO>> searchByCategory(@PathVariable String category) {
        return ResponseEntity.ok(searchService.getRestaurantsByCategory(category));
    }
}

