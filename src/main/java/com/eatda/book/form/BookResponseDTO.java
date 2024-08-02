package com.eatda.book.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponseDTO {
    private Long bookId;
    private int totalPrice;
}
