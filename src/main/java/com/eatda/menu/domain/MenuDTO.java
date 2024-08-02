package com.eatda.menu.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuDTO {
    private Long id;
    private String menuName;
    private String menuBody;
    private int price;
    private int stockQuantity;
    private Long restaurantId;

    public static MenuDTO toEntity(Menu menuEntity) {
        return MenuDTO.builder()
                .id(menuEntity.getId())
                .menuName(menuEntity.getMenuName())
                .menuBody(menuEntity.getMenuBody())
                .price(menuEntity.getPrice())
                .stockQuantity(menuEntity.getStockQuantity())
                .build();
    }
}