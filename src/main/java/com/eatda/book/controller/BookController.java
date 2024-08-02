package com.eatda.book.controller;

import com.eatda.book.form.BookRequestDTO;
import com.eatda.book.form.BookResponseDTO;
import com.eatda.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/restaurant/")
public class BookController {

    private final BookService bookService;

    //예약 (주문)
    @PostMapping("{restautant_id}/book")
    public ResponseEntity<BookResponseDTO> book (@PathVariable Long restautantId,
                                                 @RequestBody BookRequestDTO bookRequestDTO){
        try {
            BookResponseDTO bookResponseDTO= bookService.book(bookRequestDTO);
            return ResponseEntity.ok(bookResponseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // 주문 가격 조회
    @GetMapping("/book/{bookId}/totalPrice")
    public ResponseEntity<Integer> getTotalPrice(@PathVariable Long bookId) {
        try {
            int totalPrice = bookService.getTotalPrice(bookId);
            return ResponseEntity.ok(totalPrice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}
