package com.eatda.president.dto;

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
}
