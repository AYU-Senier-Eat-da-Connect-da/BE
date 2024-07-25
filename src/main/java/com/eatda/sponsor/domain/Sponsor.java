package com.eatda.sponsor.domain;

import com.eatda.child.domain.Child;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Sponsor {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long sponsor_id;

    private String sponsorName;
    private String sponsorEmail;
    private String sponsorPassword;
    private String sponsorAddress;

    @OneToOne(mappedBy = "sponsor", cascade = CascadeType.ALL)
    private Child child;
}
