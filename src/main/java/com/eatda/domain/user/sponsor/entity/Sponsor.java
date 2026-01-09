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

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
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

    public void setChild(Child child) {
        this.child = child;
    }

    public void setSponsorAmount(int sponsorAmount) {
        this.sponsorAmount = sponsorAmount;
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
