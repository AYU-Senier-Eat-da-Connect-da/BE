package com.eatda.sponsor.domain;

import com.eatda.FCM.form.NotifiableUser;
import com.eatda.child.domain.Child;
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
