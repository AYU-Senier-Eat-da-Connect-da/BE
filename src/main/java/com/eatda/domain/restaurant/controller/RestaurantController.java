package com.eatda.domain.restaurant.controller;

import com.eatda.domain.restaurant.dto.RestaurantDTO;
import com.eatda.domain.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping("/{restaurantId}")
    public ResponseEntity<RestaurantDTO> getRestaurantByPresidentId(@PathVariable Long restaurantId) {
        RestaurantDTO restaurantDTO = restaurantService.getRestaurantById(restaurantId);
        if (restaurantDTO != null) {
            return ResponseEntity.ok(restaurantDTO);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<RestaurantDTO> createRestaurant(@RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO createRestaurant = restaurantService.createRestaurant(restaurantDTO);
        if (createRestaurant != null) {
            return ResponseEntity.ok(createRestaurant);
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping("/{restaurantId}/update")
    public ResponseEntity<RestaurantDTO> updateRestaurant(@PathVariable Long restaurantId, @RequestBody RestaurantDTO restaurantDTO) {
        RestaurantDTO updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurantDTO);
        return ResponseEntity.ok(updatedRestaurant);
    }

    @DeleteMapping("/{restaurantId}")
    public ResponseEntity<String> deleteRestaurant(@PathVariable Long restaurantId) {
        restaurantService.deleteRestaurant(restaurantId);
        return new ResponseEntity<>("삭제 완료", HttpStatus.OK);
    }
}
