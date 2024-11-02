package com.eatda.restaurant.domain;


public class RestaurantPhotoDTO {
    private Long id;
    private String fileName;
    private String filePath;
    private Long restaurantId;

    public RestaurantPhotoDTO(RestaurantPhoto photo) {
        this.id = photo.getId();
        this.fileName = photo.getFileName();
        this.filePath = photo.getFilePath();
        this.restaurantId = photo.getRestaurant().getId();
    }
}
