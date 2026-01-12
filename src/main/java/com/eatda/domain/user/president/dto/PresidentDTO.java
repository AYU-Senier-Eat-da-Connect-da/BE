package com.eatda.domain.user.president.dto;

import com.eatda.domain.user.president.entity.President;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PresidentDTO {

    private Long id;
    private String presidentName;
    private String presidentEmail;
    private String presidentNumber;
    private int businessNumber;

    /**
     * President Entity -> PresidentDTO 변환
     */
    public static PresidentDTO from(President president) {
        return PresidentDTO.builder()
                .id(president.getId())
                .presidentName(president.getPresidentName())
                .presidentEmail(president.getPresidentEmail())
                .presidentNumber(president.getPresidentNumber())
                .businessNumber(Integer.parseInt(president.getBusinessNumber()))
                .build();
    }
}
