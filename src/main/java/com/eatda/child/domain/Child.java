package com.eatda.child.domain;

import com.eatda.card.domain.Card;
import com.eatda.order.domain.Order;
import com.eatda.review.domain.Review;
import com.eatda.sponsor.domain.Sponsor;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Child {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String childName;
    private String childEmail;
    private String childPassword;
    private String childNumber;
    private String childAddress;

    @OneToOne
    @JoinColumn(name = "card_id", unique = true)
    private Card card;

    @OneToOne
    @JoinColumn(name = "sponsor_id")
    private Sponsor sponsor;

    @OneToMany(mappedBy = "child")
    private List<Review> reviews;

    @OneToMany(mappedBy = "child")
    private List<Order> orders;

    public void setSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }
}
