package com.eatda.book.service;

import com.eatda.book.repository.BookRepository;
import com.eatda.child.domain.Child;
import com.eatda.menu.domain.Menu;
import com.eatda.menu.service.MenuService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BookServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    BookService bookService;

    @Autowired
    MenuService menuService;

    @Autowired
    BookRepository bookRepository;

    @Test
        public void 예약하기() throws Exception {
            //given
            Child child = new Child();
            Menu menu = createMenu("떡볶이", 5000, 10);    //이름, 가격, 수량
            //when

            //then
        }
}