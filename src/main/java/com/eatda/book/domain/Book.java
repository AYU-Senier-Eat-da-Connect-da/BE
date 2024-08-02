package com.eatda.book.domain;

import com.eatda.child.domain.Child;
import com.eatda.menu.domain.Menu;
import com.eatda.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import lombok.*;
import org.thymeleaf.standard.inline.StandardHTMLInliner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Book {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "child_id")
    private Child child;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

    private int count; // 예약 수량
    private LocalDateTime localDateTime;


    //==예약 생성 메서드==//
    public static Book createBook(Child child, Menu menu, int count) {
        return Book.builder()
                .child(child)
                .menu(menu)
                .count(count)
                .localDateTime(LocalDateTime.now())
                .build();
    }


    // 전체 가격 메서드
    public int getTotalPrice() {
        return menu.getPrice() * count;
    }

    // 메뉴를 통해 레스토랑을 가져오는 메서드
    public Restaurant getRestaurant() {
        return menu.getRestaurant();
    }
}
