package com.eatda.menu.domain;

import com.eatda.global.exception.NotEnoughStockException;
import com.eatda.restaurant.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Menu {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "menu_id")
    private Long menuId;

    private String menuName;    //메뉴명
    private String menuBody;    //메뉴 설명
    private int price;  //메뉴 가격

    private int stockQuantity;  // 재고

    //Todo: 메뉴 품절여부
    private Boolean menuStatus;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    /**
     * 상품 재고 증가
     */

    public void addStock (int quantity){
        this.stockQuantity += quantity;
    }

    /**
     * 상품 재고 감소
     */
    public void removeStock (int quantity) {
        int restStock = this.stockQuantity - quantity;
        if (restStock < 0) {
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }

}
