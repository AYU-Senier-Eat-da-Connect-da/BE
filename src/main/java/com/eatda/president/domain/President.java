package com.eatda.president.domain;

import com.eatda.restaurant.domain.Restaurant;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class President {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String businessNumber;
    private String presidentName;
    private String presidentEmail;
    private String presidentPassword;
    private String presidentNumber;

    @OneToMany(mappedBy = "president", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference  // Restaurant에서의 순환 참조 방지
    private List<Restaurant> restaurant = new ArrayList<>();
}
