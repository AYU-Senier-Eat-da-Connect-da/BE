package com.eatda.child.form;

import com.eatda.child.domain.Child;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChildDTO {
    private Long id;
    private String childName;
    private String childEmail;
    private String childNumber;
    private String childAddress;

    public static ChildDTO toEntity(Child child) {
        return ChildDTO.builder()
                .id(child.getId())
                .childName(child.getChildName())
                .childEmail(child.getChildEmail())
                .childNumber(child.getChildNumber())
                .childAddress(child.getChildAddress())
                .build();
    }
}
