package com.eatda.book.service;

import com.eatda.book.domain.Book;
import com.eatda.book.form.BookRequestDTO;
import com.eatda.book.form.BookResponseDTO;
import com.eatda.book.repository.BookRepository;
import com.eatda.child.domain.Child;
import com.eatda.child.repository.ChildRepository;
import com.eatda.menu.domain.Menu;
import com.eatda.menu.repository.MenuRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @Autowired BookService bookService;
    @Autowired BookRepository bookRepository;
    @Autowired ChildRepository childRepository;
    @Autowired MenuRepository menuRepository;

    @Test
    public void 음식예약() throws Exception {
        //given
        Child child = Child.builder()
                .childName("홍길동")
                .childEmail("kim123@gmail.com")
                .childPassword("123123")
                .childNumber("01012345678")
                .childAddress("경기도 세모시 네모아파트 101동 201호")
                .build();
        Child savedChild = childRepository.save(child);

        Menu menu = Menu.builder()
            .menuName("갈비찜 덮밥")
            .menuBody("내가 제일 좋아하는 갈비찜 덮밥")
            .price(10000)
            .stockQuantity(100)
            .build();
        Menu savedMenu = menuRepository.save(menu);

        int count = 2;  // 주문한 메뉴 수량

        //when
        BookRequestDTO bookRequestDTO = BookRequestDTO.builder()
                .childId(savedChild.getChildId())
                .menuId(savedMenu.getId())
                .count(count)
                .build();
        BookResponseDTO bookResponseDTO = bookService.book(bookRequestDTO);

        //then
        Optional<Book> book = bookRepository.findById(bookResponseDTO.getBookId());
        assertTrue(book.isPresent());

        // 1. 예약(주문)한 음식의 수가 정확해야한다.
        assertEquals(2, book.get().getCount());

        // 2. 주문 가격은 가격 * 수량이다.
        assertEquals(20000, book.get().getTotalPrice());
    }
}