package com.eatda.domain.restaurant.dto;

import com.eatda.domain.menu.dto.MenuDTO;
import com.eatda.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
public class RestaurantDTO {
    private Long id;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantNumber;
    private String restaurantBody;
    private String restaurantCategory;
    private Long presidentId;
    private List<MenuDTO> menus;

    public RestaurantDTO(Restaurant restaurant) {
        this.id = restaurant.getId();
        this.restaurantName = restaurant.getRestaurantName();
        this.restaurantAddress = restaurant.getRestaurantAddress();
        this.restaurantNumber = restaurant.getRestaurantNumber();
        this.restaurantBody = restaurant.getRestaurantBody();
        this.restaurantCategory = restaurant.getRestaurantCategory();
        this.presidentId = restaurant.getPresident().getId();
    }

    public RestaurantDTO(Long id, String restaurantName, String restaurantAddress, String restaurantNumber, String restaurantBody, String restaurantCategory) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantNumber = restaurantNumber;
        this.restaurantBody = restaurantBody;
        this.restaurantCategory = restaurantCategory;
    }

    public RestaurantDTO(Long id, String restaurantName, String restaurantAddress, String restaurantNumber, String restaurantBody, String restaurantCategory, Long presidentId, List<MenuDTO> menus) {
        this.id = id;
        this.restaurantName = restaurantName;
        this.restaurantAddress = restaurantAddress;
        this.restaurantNumber = restaurantNumber;
        this.restaurantBody = restaurantBody;
        this.restaurantCategory = restaurantCategory;
        this.presidentId = presidentId;
        this.menus = menus;
    }

    public static RestaurantDTO toEntity(Restaurant restaurantEntity) {
        return RestaurantDTO.builder()
                .id(restaurantEntity.getId())
                .restaurantName(restaurantEntity.getRestaurantName())
                .restaurantAddress(restaurantEntity.getRestaurantAddress())
                .restaurantNumber(restaurantEntity.getRestaurantNumber())
                .restaurantBody(restaurantEntity.getRestaurantBody())
                .restaurantCategory(restaurantEntity.getRestaurantCategory())
                .presidentId(restaurantEntity.getId())
                .build();
    }
}
