package com.eatda.restaurant.controller;

import com.eatda.restaurant.domain.RestaurantPhoto;
import com.eatda.restaurant.service.RestaurantPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant")
public class RestaurantPhotoController {

    private final RestaurantPhotoService restaurantPhotoService;

    // 사진 등록 및 업데이트
    @PostMapping("/{restaurantId}/photo")
    public ResponseEntity<String> uploadOrUpdateRestaurantPhoto(@PathVariable Long restaurantId,
                                                                @RequestParam("photo") MultipartFile file) {
        try {
            String result = restaurantPhotoService.uploadOrUpdateRestaurantPhoto(restaurantId, file);
            return ResponseEntity.ok(result);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Photo upload/update failed");
        }
    }

    // 사진 조회
    @GetMapping("/{restaurantId}/photo")
    public ResponseEntity<RestaurantPhoto> getRestaurantPhoto(@PathVariable Long restaurantId) {
        try {
            RestaurantPhoto photo = restaurantPhotoService.getRestaurantPhoto(restaurantId);
            return ResponseEntity.ok(photo);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
