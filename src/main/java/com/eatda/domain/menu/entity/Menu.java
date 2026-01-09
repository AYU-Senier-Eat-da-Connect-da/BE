package com.eatda.domain.menu.entity;

import com.eatda.domain.restaurant.entity.Restaurant;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String menuName;
    private String menuBody;
    private Boolean menuStatus;
    private int price;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")

    private Restaurant restaurant;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<MenuPhoto> photos;

    public void updateMenu(String menuName, String menuBody, boolean menuStatus, int price) {
        this.menuName = menuName;
        this.menuBody = menuBody;
        this.menuStatus = menuStatus;
        this.price = price;
    }
}
