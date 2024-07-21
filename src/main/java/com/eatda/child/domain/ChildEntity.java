package com.eatda.child.domain;

import com.eatda.card.domain.CardEntity;
import com.eatda.review.domain.ReviewEntity;
import com.eatda.sponsor.domain.SponsorEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
public class ChildEntity {

    @Id @GeneratedValue
    @Column(name = "child_id")
    private int child_id;

    private String child_name;

    private String child_email;

    private String child_password;

    private String child_number;

    private String child_address;

    /*
    private CardEntity card;

    private SponsorEntity sponsor;
*/
    @OneToMany(mappedBy = "child", fetch = FetchType.LAZY)
    private List<ReviewEntity> reviews;
}
