package com.eatda.child.service;

import com.eatda.child.domain.Child;
import com.eatda.child.form.ChildDTO;
import com.eatda.child.repository.ChildRepository;
import com.eatda.sponsor.domain.Sponsor;
import com.eatda.sponsor.form.SponsorDTO;
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
            throw new RuntimeException("Sponsor not found");
        }
    }

    @Transactional
    public void updateAmounts(Long childId, int amount) {
        Child child = childRepository.findById(childId)
                .orElseThrow(()->new IllegalArgumentException("Invalid child ID"));

        child.setChildAmount(child.getChildAmount()+amount);

        childRepository.save(child);
    }
}
