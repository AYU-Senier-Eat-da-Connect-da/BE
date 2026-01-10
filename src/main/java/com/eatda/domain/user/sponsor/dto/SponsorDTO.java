package com.eatda.domain.user.sponsor.dto;

import com.eatda.domain.user.child.dto.ChildDTO;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.sponsor.entity.Sponsor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SponsorDTO {
    private Long id;
    private String sponsorName;
    private String sponsorEmail;
    private String sponsorAddress;
    private String sponsorNumber;
    private int sponsorAmount;
    private String fcmToken;
    private ChildDTO child;

    public static SponsorDTO toEntity(Sponsor sponsor) {
        Child child = sponsor.getChild();
        ChildDTO childDTO = null;

        if (child != null) {
            childDTO = ChildDTO.builder()
                    .id(child.getId())
                    .childName(child.getChildName())
                    .childEmail(child.getChildEmail())
                    .childNumber(child.getChildNumber())
                    .childAddress(child.getChildAddress())
                    .build();
        }

        return SponsorDTO.builder()
                .id(sponsor.getId())
                .sponsorName(sponsor.getSponsorName())
                .sponsorEmail(sponsor.getSponsorEmail())
                .sponsorAddress(sponsor.getSponsorAddress())
                .sponsorNumber(sponsor.getSponsorAddress())
                .sponsorAmount(sponsor.getSponsorAmount())
                .child(childDTO)
                .build();
    }
}
