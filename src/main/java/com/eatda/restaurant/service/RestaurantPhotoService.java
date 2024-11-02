package com.eatda.restaurant.service;

import com.eatda.restaurant.domain.Restaurant;
import com.eatda.restaurant.domain.RestaurantPhoto;
import com.eatda.restaurant.repository.RestaurantPhotoRepository;
import com.eatda.restaurant.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantPhotoService {

    private final String UPLOAD_DIR = System.getProperty("user.home") + "/Desktop/photo/";

    private final RestaurantRepository restaurantRepository;
    private final RestaurantPhotoRepository restaurantPhotoRepository;

    // 사진 등록 및 업데이트
// 사진 등록 및 업데이트
    public String uploadOrUpdateRestaurantPhoto(Long restaurantId, MultipartFile file) throws IOException {
        Optional<Restaurant> restaurant = restaurantRepository.findById(restaurantId);
        if (restaurant.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }

        // Ensure the upload directory exists
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        File dest = new File(UPLOAD_DIR + fileName);
        file.transferTo(dest);

        // 기존 사진 삭제 (있다면)
        RestaurantPhoto existingPhoto = restaurant.get().getPhoto();
        if (existingPhoto != null) {
            File oldFile = new File(existingPhoto.getFilePath());
            if (oldFile.exists()) {
                oldFile.delete();
            }
            restaurantPhotoRepository.delete(existingPhoto);
        }

        // 새로운 사진 저장
        RestaurantPhoto restaurantPhoto = RestaurantPhoto.builder()
                .fileName(file.getOriginalFilename())
                .filePath(UPLOAD_DIR + fileName)
                .restaurant(restaurant.get())
                .build();
        restaurantPhotoRepository.save(restaurantPhoto);

        return "Photo uploaded/updated successfully";
    }


    // 사진 조회
    public RestaurantPhoto getRestaurantPhoto(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        return restaurant.getPhoto();
    }

}
