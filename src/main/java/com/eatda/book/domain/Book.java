package com.eatda.book.domain;

import com.eatda.child.domain.Child;
import com.eatda.restaurant.domain.Restaurant;
import jakarta.persistence.*;
import lombok.*;
import org.thymeleaf.standard.inline.StandardHTMLInliner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(mappedBy = "book")
    private List<BookMenu> bookMenus = new ArrayList<>();

    private LocalDateTime localDateTime;

    //==예약 생성 메서드==//
    public static Book createBook(Child child, BookMenu... bookMenus) {
        Book book = new Book();

        book.setChild(child);
        for (BookMenu bookMenu : bookMenus){
            book.addBookMenu(bookMenu);
        }
        return book;
    }

    //연관관계 메서드
    public void setChild(Child child) {
        this.child = child;
        child.getBooks().add(this);
    }

    public void addBookMenu(BookMenu bookMenu){
        bookMenus.add(bookMenu);
        bookMenu.setBook(this);
    }

}
