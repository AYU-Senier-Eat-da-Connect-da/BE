package com.eatda.book.domain;

import com.eatda.menu.domain.Menu;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class BookMenu {

    @Id @GeneratedValue
    @Column(name = "book_menu_id")
    private Long bookMenuId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private Book book;

    private int price; //주문 가격
    private int count;  //주문 수량

    //==예약 메뉴 생성 메서드==//
    public static BookMenu createBookMenu(Menu menu, int price, int count) {
        BookMenu bookMenu = new BookMenu();

        bookMenu.setMenu(menu);
        bookMenu.setPrice(price);
        bookMenu.setCount(count);

        menu.removeStock(count);
        return bookMenu;
    }
}
