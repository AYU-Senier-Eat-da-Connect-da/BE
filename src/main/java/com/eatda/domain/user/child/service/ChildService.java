package com.eatda.domain.user.child.service;

import com.eatda.domain.user.child.dto.ChildDTO;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import com.eatda.domain.user.sponsor.dto.SponsorDTO;
import com.eatda.domain.user.sponsor.entity.Sponsor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    public List<ChildDTO> getAllchildren() {
        List<Child> children = childRepository.findAll();
        List<ChildDTO> childrenDTO = new ArrayList<>();

        for (Child child : children) {
            childrenDTO.add(ChildDTO.toEntity(child));
        }

        return childrenDTO;
    }

    public ChildDTO getChildById(Long childId) {
        Optional<Child> childOptional = childRepository.findById(childId);

        if(childOptional.isPresent()){
            Child child = childOptional.get();
            return ChildDTO.toEntity(child);
        }

        return null;
    }

    public SponsorDTO findSponsorForChild(Long childId) {
        Optional<Child> childOptional = childRepository.findById(childId);

        if (childOptional.isPresent()) {
            Child child = childOptional.get();
            Sponsor sponsor = child.getSponsor();

            if (sponsor != null) {
                return SponsorDTO.toEntity(sponsor);
            }
        }

        return null;
    }

    public ChildDTO getChildInfo(Long childId) {
        Optional<Child> childOptional = childRepository.findById(childId);
        if (childOptional.isPresent()) {
            Child child = childOptional.get();

            return ChildDTO.builder()
                    .id(child.getId())
                    .childName(child.getChildName())
                    .childNumber(child.getChildNumber())
                    .childEmail(child.getChildEmail())
                    .childAddress(child.getChildAddress())
                    .childAmount(child.getChildAmount())
                    .build();
        } else {
            throw new RuntimeException("Child not found");
        }
    }

    @Transactional
    public void updateAmounts(Long childId, int amount) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid child ID"));

        child.setChildAmount(child.getChildAmount() + amount);
        childRepository.save(child);
    }
}
