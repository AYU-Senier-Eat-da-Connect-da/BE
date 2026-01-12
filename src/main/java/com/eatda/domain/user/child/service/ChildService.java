package com.eatda.domain.user.child.service;

import com.eatda.domain.user.child.dto.ChildDTO;
import com.eatda.domain.user.child.entity.Child;
import com.eatda.domain.user.child.repository.ChildRepository;
import com.eatda.domain.user.sponsor.dto.SponsorDTO;
import com.eatda.domain.user.sponsor.entity.Sponsor;
import com.eatda.global.exception.CustomException;
import com.eatda.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChildService {

    private final ChildRepository childRepository;

    public List<ChildDTO> getAllchildren() {
        List<Child> children = childRepository.findAll();
        return ChildDTO.from(children);
    }

    public ChildDTO getChildById(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));
        return ChildDTO.from(child);
    }

    public SponsorDTO findSponsorForChild(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));

        Sponsor sponsor = child.getSponsor();
        if (sponsor == null) {
            throw new CustomException(ErrorCode.SPONSOR_NOT_FOUND);
        }

        return SponsorDTO.from(sponsor);
    }

    public ChildDTO getChildInfo(Long childId) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));
        return ChildDTO.from(child);
    }

    @Transactional
    public void updateAmounts(Long childId, int amount) {
        Child child = childRepository.findById(childId)
                .orElseThrow(() -> new CustomException(ErrorCode.CHILD_NOT_FOUND));

        child.receiveSupport(amount);
    }
}
