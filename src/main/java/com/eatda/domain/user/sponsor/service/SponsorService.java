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

@Service
@RequiredArgsConstructor
public class SponsorService {

    private final SponsorRepository sponsorRepository;
    private final ChildRepository childRepository;

    public List<ChildDTO> getAllChildren() {
        List<Child> children = childRepository.findAll();
        return ChildDTO.from(children);
    }

    public ChildDTO getChildById(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));
        return ChildDTO.from(child);
    }

    public List<ChildDTO> getChildrenBySponsorId(Long sponsorId) {
        List<Child> children = childRepository.findBySponsorId(sponsorId);
        return ChildDTO.from(children);
    }

    @Transactional
    public SponsorDTO addChildToSponsor(Long sponsorId, Long childId) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new CustomException(ErrorCode.SPONSOR_NOT_FOUND));
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));

        sponsor.linkChild(child);
        sponsorRepository.save(sponsor);

        return SponsorDTO.from(sponsor);
    }

    @Transactional
    public SponsorDTO removeChildFromSponsor(Long sponsorId, Long childId) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new CustomException(ErrorCode.SPONSOR_NOT_FOUND));
        childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));

        sponsor.unlinkChild();
        sponsorRepository.save(sponsor);

        return SponsorDTO.from(sponsor);
    }

    @Transactional
    public void updateAmounts(Long sponsorId, int amount) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new CustomException(ErrorCode.SPONSOR_NOT_FOUND));

        sponsor.supportChild(amount);
        sponsorRepository.save(sponsor);
    }

    public SponsorDTO getSponsorInfo(Long sponsorId) {
        Sponsor sponsor = sponsorRepository.findById(sponsorId)
                .orElseThrow(() -> new CustomException(ErrorCode.SPONSOR_NOT_FOUND));
        return SponsorDTO.from(sponsor);
    }
}
