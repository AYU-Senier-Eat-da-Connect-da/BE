package com.eatda.book.form;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookRequestDTO {
    private Long childId;
    private Long menuId;
    private int count;
}
