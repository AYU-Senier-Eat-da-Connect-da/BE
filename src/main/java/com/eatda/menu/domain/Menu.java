package com.eatda.menu.domain;

import com.eatda.restaurant.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

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
    @JsonIgnore  // 직렬화에서 완전히 무시
    private Restaurant restaurant;

    public void updateMenu(String menuName, String menuBody, boolean menuStatus, int price) {
        this.menuName = menuName;
        this.menuBody = menuBody;
        this.menuStatus = menuStatus;
        this.price = price;
    }
}
