package com.eatda.sponsor.form;

import com.eatda.child.domain.Child;
import com.eatda.child.form.ChildDTO;
import com.eatda.sponsor.domain.Sponsor;
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
