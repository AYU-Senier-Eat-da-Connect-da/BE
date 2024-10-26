package com.eatda.menu.domain;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MenuDTO {
    private Long id;
    private String menuName;
    private String menuBody;
    private int price;
    private boolean menuStatus;
    private Long restaurantId;

    public MenuDTO(Long id, String menuName, String menuBody, int price, Boolean menuStatus) {
        this.id = id;
        this.menuName = menuName;
        this.menuBody = menuBody;
        this.price = price;
        this.menuStatus = menuStatus;
    }

    public static MenuDTO toEntity(Menu menuEntity) {
        return MenuDTO.builder()
                .id(menuEntity.getId())
                .menuName(menuEntity.getMenuName())
                .menuStatus(menuEntity.getMenuStatus())
                .menuBody(menuEntity.getMenuBody())
                .price(menuEntity.getPrice())
                .build();
    }
}