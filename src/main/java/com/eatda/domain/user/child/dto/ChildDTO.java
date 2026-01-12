package com.eatda.domain.user.child.dto;

import com.eatda.domain.user.child.entity.Child;
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
public class ChildDTO {
    private Long id;
    private String childName;
    private String childEmail;
    private String childNumber;
    private String childAddress;
    private int childAmount;

    /**
     * Child Entity -> ChildDTO 변환
     */
    public static ChildDTO from(Child child) {
        return ChildDTO.builder()
                .id(child.getId())
                .childName(child.getChildName())
                .childEmail(child.getChildEmail())
                .childNumber(child.getChildNumber())
                .childAddress(child.getChildAddress())
                .childAmount(child.getChildAmount())
                .build();
    }

    /**
     * List<Child> -> List<ChildDTO> 변환
     */
    public static List<ChildDTO> from(List<Child> children) {
        List<ChildDTO> result = new ArrayList<>();
        for (Child child : children) {
            result.add(from(child));
        }
        return result;
    }

    /**
     * @deprecated Use {@link #from(Child)} instead
     */
    @Deprecated
    public static ChildDTO toEntity(Child child) {
        return from(child);
    }
}
