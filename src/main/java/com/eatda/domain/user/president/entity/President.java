package com.eatda.domain.user.president.entity;

import com.eatda.global.infra.fcm.NotifiableUser;
import com.eatda.domain.restaurant.entity.Restaurant;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class President implements NotifiableUser {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessNumber;
    private String presidentName;
    private String presidentEmail;
    private String presidentPassword;
    private String presidentNumber;
    private String fcmToken;

    @OneToMany(mappedBy = "president", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)

    private List<Restaurant> restaurant = new ArrayList<>();

    @Override
    public String getFcmToken() {
        return this.fcmToken;
    }

    @Override
    public Long getId() {
        return this.id;
    }
}
