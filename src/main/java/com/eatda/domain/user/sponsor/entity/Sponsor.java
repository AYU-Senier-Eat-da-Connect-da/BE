package com.eatda.domain.user.sponsor.entity;

import com.eatda.global.infra.fcm.NotifiableUser;
import com.eatda.domain.user.child.entity.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Sponsor implements NotifiableUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sponsorName;
    private String sponsorEmail;
    private String sponsorPassword;
    private String sponsorAddress;
    private String sponsorNumber;
    private int sponsorAmount;
    private String fcmToken;

    @OneToOne(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private Child child;

    // ===== 도메인 비즈니스 로직 =====

    /**
     * 후원금을 추가합니다.
     * @param amount 추가할 금액
     */
    public void addSponsorAmount(int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("후원 금액은 0보다 커야 합니다.");
        }
        this.sponsorAmount += amount;
    }

    /**
     * 후원 아동을 연결합니다.
     * @param child 연결할 아동
     */
    public void linkChild(Child child) {
        this.child = child;
        if (child != null) {
            child.linkSponsor(this);
        }
    }

    /**
     * 후원 아동 연결을 해제합니다.
     */
    public void unlinkChild() {
        if (this.child != null) {
            this.child.unlinkSponsor();
            this.child = null;
        }
    }

    /**
     * 연결된 아동에게 후원금을 전달합니다.
     * @param amount 전달할 금액
     */
    public void supportChild(int amount) {
        addSponsorAmount(amount);
        if (this.child != null) {
            this.child.receiveSupport(amount);
        }
    }

    /**
     * 후원 아동이 연결되어 있는지 확인합니다.
     */
    public boolean hasChild() {
        return this.child != null;
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

