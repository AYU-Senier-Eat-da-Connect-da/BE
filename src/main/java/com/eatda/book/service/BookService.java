package com.eatda.book.service;

import com.eatda.book.domain.Book;
import com.eatda.book.form.BookRequestDTO;
import com.eatda.book.form.BookResponseDTO;
import com.eatda.book.repository.BookRepository;
import com.eatda.child.domain.Child;
import com.eatda.child.repository.ChildRepository;
import com.eatda.menu.domain.Menu;
import com.eatda.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public BookResponseDTO book(BookRequestDTO bookRequestDTO) {

        Long childId = bookRequestDTO.getChildId();
        Long menuId = bookRequestDTO.getMenuId();
        int count = bookRequestDTO.getCount();

        //엔티티 조회
        Optional<Child> child = childRepository.findById(childId);
        Optional<Menu> menu = menuRepository.findById(menuId);

        if (child.isEmpty() || menu.isEmpty()) {
            throw new RuntimeException("Child or Menu not found");
        }

        //예약 생성
        Book book = Book.createBook(child.get(), menu.get(), count);

        //예약 저장
        Book savedBook = bookRepository.save(book);

        // 응답 DTO 생성
        return BookResponseDTO.builder()
                .bookId(savedBook.getBookId())
                .totalPrice(savedBook.getTotalPrice())
                .build();
    }


    /**
     * 전체 주문 가격 조회
     */
    @Transactional(readOnly = true)
    public int getTotalPrice(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        return book.map(Book::getTotalPrice).orElse(0);
    }

}
