package com.eatda.picture.domain;

import com.eatda.review.domain.Review;
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
public class Picture {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String originName;
    private String storedName;

    @ManyToOne
    @JoinColumn(name = "review_id")
    private Review review;

}
