package com.eatda.domain.user.child.entity;

import com.eatda.global.exception.CustomException;
import com.eatda.global.exception.ErrorCode;
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

    // ===== 도메인 비즈니스 로직 =====

    /**
     * 후원금을 수령합니다.
     * @param amount 수령할 금액
     */
    public void receiveSupport(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("후원 금액은 0보다 커야 합니다.");
        }
        this.childAmount += amount;
    }

    /**
     * 결제를 진행합니다. 잔액이 부족하면 예외를 발생시킵니다.
     * @param amount 결제할 금액
     */
    public void pay(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("결제 금액은 0보다 커야 합니다.");
        }
        if (this.childAmount < amount) {
            throw new CustomException(ErrorCode.INSUFFICIENT_BALANCE);
        }
        this.childAmount -= amount;
    }

    /**
     * 결제 가능 여부를 확인합니다.
     * @param amount 결제할 금액
     * @return 결제 가능 여부
     */
    public boolean canPay(int amount) {
        return this.childAmount >= amount;
    }

    /**
     * 후원자를 연결합니다. (내부용)
     * @param sponsor 연결할 후원자
     */
    public void linkSponsor(Sponsor sponsor) {
        this.sponsor = sponsor;
    }

    /**
     * 후원자 연결을 해제합니다. (내부용)
     */
    public void unlinkSponsor() {
        this.sponsor = null;
    }

    /**
     * 후원자가 연결되어 있는지 확인합니다.
     */
    public boolean hasSponsor() {
        return this.sponsor != null;
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

