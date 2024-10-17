package com.eatda.card.domain;

import com.eatda.child.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cardNumber;  //카드 번호
    private String cardPassword;    //카드 비밀번호
    private int cardBalance;    //카드 잔액

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    private Child child;
}
