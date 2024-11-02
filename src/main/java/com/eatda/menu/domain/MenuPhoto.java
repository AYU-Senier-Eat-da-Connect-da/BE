package com.eatda.menu.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
public class MenuPhoto {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;   // 파일명
    private String filePath;   // 파일 경로 또는 URL

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_id")
    private Menu menu;
}
