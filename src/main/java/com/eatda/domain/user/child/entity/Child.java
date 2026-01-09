package com.eatda.domain.user.child.entity;

import com.eatda.global.infra.fcm.NotifiableUser;
import com.eatda.domain.order.entity.Order;
import com.eatda.domain.review.entity.Review;
import com.eatda.domain.user.sponsor.entity.Sponsor;
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
public class Child implements NotifiableUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String childName;
    private String childEmail;
    private String childPassword;
    private String childNumber;
    private String childAddress;
    private int childAmount;
    private String fcmToken;

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

    public void setChildAmount(int childAmount) {
        this.childAmount = childAmount;
    }

    @Override
    public String getFcmToken() {
        return this.fcmToken;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
