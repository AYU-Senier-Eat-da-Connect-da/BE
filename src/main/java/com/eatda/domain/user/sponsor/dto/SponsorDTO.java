package com.eatda.domain.user.sponsor.dto;

import com.eatda.domain.user.child.dto.ChildDTO;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.sponsor.entity.Sponsor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * Sponsor Entity -> SponsorDTO 변환
     */
    public static SponsorDTO from(Sponsor sponsor) {
        Child child = sponsor.getChild();
        ChildDTO childDTO = null;

        if (child != null) {
            childDTO = ChildDTO.from(child);
        }

        return SponsorDTO.builder()
                .id(sponsor.getId())
                .sponsorName(sponsor.getSponsorName())
                .sponsorEmail(sponsor.getSponsorEmail())
                .sponsorAddress(sponsor.getSponsorAddress())
                .sponsorNumber(sponsor.getSponsorNumber())
                .sponsorAmount(sponsor.getSponsorAmount())
                .child(childDTO)
                .build();
    }

    /**
     * List<Sponsor> -> List<SponsorDTO> 변환
     */
    public static List<SponsorDTO> from(List<Sponsor> sponsors) {
        List<SponsorDTO> result = new ArrayList<>();
        for (Sponsor sponsor : sponsors) {
            result.add(from(sponsor));
        }
        return result;
    }

    /**
     * @deprecated Use {@link #from(Sponsor)} instead
     */
    @Deprecated
    public static SponsorDTO toEntity(Sponsor sponsor) {
        return from(sponsor);
    }
}
