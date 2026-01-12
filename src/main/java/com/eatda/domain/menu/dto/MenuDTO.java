package com.eatda.domain.menu.dto;

import com.eatda.domain.menu.entity.Menu;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Menu Entity -> MenuDTO 변환
     */
    public static MenuDTO from(Menu menu) {
        return MenuDTO.builder()
                .id(menu.getId())
                .menuName(menu.getMenuName())
                .menuStatus(menu.getMenuStatus())
                .menuBody(menu.getMenuBody())
                .price(menu.getPrice())
                .build();
    }

    /**
     * List<Menu> -> List<MenuDTO> 변환
     */
    public static List<MenuDTO> from(List<Menu> menus) {
        List<MenuDTO> result = new ArrayList<>();
        for (Menu menu : menus) {
            result.add(from(menu));
        }
        return result;
    }

    /**
     * @deprecated Use {@link #from(Menu)} instead
     */
    @Deprecated
    public static MenuDTO toEntity(Menu menuEntity) {
        return from(menuEntity);
    }
}
