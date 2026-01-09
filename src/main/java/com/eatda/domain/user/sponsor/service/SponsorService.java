package com.eatda.domain.user.sponsor.service;

import com.eatda.domain.user.child.dto.ChildDTO;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import com.eatda.domain.user.sponsor.dto.SponsorDTO;
import com.eatda.domain.user.sponsor.entity.Sponsor;
import com.eatda.domain.user.sponsor.repository.SponsorRepository;
import com.eatda.global.exception.CustomException;
import com.eatda.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SponsorService {

    private final SponsorRepository sponsorRepository;
    private final ChildRepository childRepository;

    public List<ChildDTO> getAllChildren() {
        List<Child> children = childRepository.findAll();
        return children.stream()
                .map(ChildDTO::toEntity)
                .collect(Collectors.toList());
    }

    public ChildDTO getChildById(Long childId) {
        Optional<Child> childOptional = childRepository.findById(childId);
        if (childOptional.isPresent()) {
            return ChildDTO.toEntity(childOptional.get());
        }
        return null;
    }

    public List<ChildDTO> getChildrenBySponsorId(Long sponsorId) {
        List<Child> children = childRepository.findBySponsorId(sponsorId);
        return children.stream()
                .map(ChildDTO::toEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public SponsorDTO addChildToSponsor(Long sponsorId, Long childId) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.findById(sponsorId);
        Optional<Child> childOptional = childRepository.findById(childId);

        if (sponsorOptional.isPresent() && childOptional.isPresent()) {
            Sponsor sponsor = sponsorOptional.get();
            Child child = childOptional.get();

            child.setSponsor(sponsor);
            sponsor.setChild(child);

            sponsorRepository.save(sponsor);
            childRepository.save(child);

            return SponsorDTO.toEntity(sponsor);
        }
        return null;
    }

    @Transactional
    public SponsorDTO removeChildFromSponsor(Long sponsorId, Long childId) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.findById(sponsorId);
        Optional<Child> childOptional = childRepository.findById(childId);

        if (sponsorOptional.isPresent() && childOptional.isPresent()) {
            Sponsor sponsor = sponsorOptional.get();
            Child child = childOptional.get();

            child.setSponsor(null);
            sponsor.setChild(null);

            sponsorRepository.save(sponsor);
            childRepository.save(child);

            return SponsorDTO.toEntity(sponsor);
        }
        return null;
    }

    @Transactional
    public void updateAmounts(Long sponsorId, int amount) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new CustomException(ErrorCode.SPONSOR_NOT_FOUND));

        sponsor.setSponsorAmount(sponsor.getSponsorAmount() + amount);

        Child child = sponsor.getChild();
        if (child != null) {
            child.setChildAmount(child.getChildAmount() + amount);
            childRepository.save(child);
        }

        sponsorRepository.save(sponsor);
    }

    public SponsorDTO getSponsorInfo(Long sponsorId) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.findById(sponsorId);
        if (sponsorOptional.isPresent()) {
            Sponsor sponsor = sponsorOptional.get();
            return SponsorDTO.toEntity(sponsor);
        } else {
            throw new CustomException(ErrorCode.SPONSOR_NOT_FOUND);
        }
    }
}
