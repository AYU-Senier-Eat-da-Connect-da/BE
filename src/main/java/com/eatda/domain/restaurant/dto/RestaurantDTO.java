package com.eatda.domain.restaurant.dto;

import com.eatda.domain.menu.dto.MenuDTO;
import com.eatda.domain.menu.entity.Menu;
import com.eatda.domain.restaurant.entity.Restaurant;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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

    /**
     * Restaurant Entity -> RestaurantDTO 변환 (메뉴 미포함)
     */
    public static RestaurantDTO from(Restaurant restaurant) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantAddress(restaurant.getRestaurantAddress())
                .restaurantNumber(restaurant.getRestaurantNumber())
                .restaurantBody(restaurant.getRestaurantBody())
                .restaurantCategory(restaurant.getRestaurantCategory())
                .presidentId(restaurant.getPresident().getId())
                .build();
    }

    /**
     * Restaurant Entity -> RestaurantDTO 변환 (메뉴 포함)
     */
    public static RestaurantDTO fromWithMenus(Restaurant restaurant, List<Menu> menus) {
        return RestaurantDTO.builder()
                .id(restaurant.getId())
                .restaurantName(restaurant.getRestaurantName())
                .restaurantAddress(restaurant.getRestaurantAddress())
                .restaurantNumber(restaurant.getRestaurantNumber())
                .restaurantBody(restaurant.getRestaurantBody())
                .restaurantCategory(restaurant.getRestaurantCategory())
                .presidentId(restaurant.getPresident().getId())
                .menus(MenuDTO.from(menus))
                .build();
    }

    /**
     * List<Restaurant> -> List<RestaurantDTO> 변환
     */
    public static List<RestaurantDTO> from(List<Restaurant> restaurants) {
        List<RestaurantDTO> result = new ArrayList<>();
        for (Restaurant restaurant : restaurants) {
            result.add(from(restaurant));
        }
        return result;
    }

    /**
     * @deprecated Use {@link #from(Restaurant)} instead
     */
    @Deprecated
    public static RestaurantDTO toEntity(Restaurant restaurantEntity) {
        return from(restaurantEntity);
    }
}
