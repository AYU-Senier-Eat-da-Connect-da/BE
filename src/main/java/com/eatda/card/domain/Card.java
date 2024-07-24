package com.eatda.card.domain;

import com.eatda.child.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cardNumber;
    private String cardPassword;
    private int cardBalance;

    @OneToOne(mappedBy = "card", cascade = CascadeType.ALL)
    private Child child;
}
