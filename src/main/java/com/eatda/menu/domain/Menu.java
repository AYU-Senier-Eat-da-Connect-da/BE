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
    private Long id;

    private String menuName;
    private String menuBody;

//    Todo: 품절여부 삭제 후 음식수량으로 바꿈.
//    private Boolean menuStatus;

    private int price;
    private int stockQuantity;  //음식 수량

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference //순환참조 방지
    private Restaurant restaurant;


    public void updateMenu(String menuName, String menuBody, int price, int stockQuantity) {
        this.menuName = menuName;
        this.menuBody = menuBody;
        this.stockQuantity = stockQuantity;
        this.price = price;
    }

    /**
     * 음식 수량 증가
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 음식 수량 감소
     */
    public void removeStock(int quantity) {
        int restStock = this.stockQuantity - quantity;

        if (restStock < 0){
            throw new NotEnoughStockException("need more stock");
        }
        this.stockQuantity = restStock;
    }
}

