package com.eatda.book.service;

import com.eatda.book.domain.Book;
import com.eatda.book.domain.BookMenu;
import com.eatda.book.repository.BookRepository;
import com.eatda.child.domain.Child;
import com.eatda.child.repository.ChildRepository;
import com.eatda.menu.domain.Menu;
import com.eatda.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final ChildRepository childRepository;
    private final MenuRepository menuRepository;

    /**
     * 예약 (주문)
     */
    @Transactional
    public Long book(Long childId, Long menuId, int count){

        //엔티티 조회
        Child child = childRepository.findOne(childId);
        Menu menu = menuRepository.findOne(menuId);

        //예약 메뉴 생성
        BookMenu bookMenu = BookMenu.createBookMenu(menu, menu.getPrice(), count);

        //예약 생성
        Book book = Book.createBook(child, bookMenu);

        //예약 저장
        bookRepository.save(book);

        return book.getBookId();
    }


    /**
     * Todo: 전체 주문 가격 조회
     *
    public int getTotalPrice() {
        int totalPrice = 0;

        for (BookMenu bookMenu : ) {
            totalPrice += bookMenu.getTotalPrice();
        }
        return totalPrice;
    }
     */
}
