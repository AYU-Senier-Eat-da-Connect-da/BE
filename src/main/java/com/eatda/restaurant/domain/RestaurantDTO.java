package com.eatda.restaurant.domain;

import com.eatda.menu.domain.MenuDTO;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RestaurantDTO {
    private Long id;
    private String restaurantName;
    private String restaurantAddress;
    private String restaurantNumber;
    private String restaurantBody;
    private Long presidentId;
    private List<MenuDTO> menus;

    public static RestaurantDTO toEntity(Restaurant restaurantEntity){
        return RestaurantDTO.builder()
                .id(restaurantEntity.getId())
                .restaurantName(restaurantEntity.getRestaurantName())
                .restaurantAddress(restaurantEntity.getRestaurantAddress())
                .restaurantNumber(restaurantEntity.getRestaurantNumber())
                .restaurantBody(restaurantEntity.getRestaurantBody())
                .presidentId(restaurantEntity.getId())
                .build();
    }
}
