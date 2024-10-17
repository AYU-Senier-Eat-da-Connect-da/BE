package com.eatda.sponsor.service;

import com.eatda.child.domain.Child;
import com.eatda.child.form.ChildDTO;
import com.eatda.child.repository.ChildRepository;
import com.eatda.sponsor.domain.Sponsor;
import com.eatda.sponsor.form.SponsorDTO;
import com.eatda.sponsor.repository.SponsorRepository;
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

    public List<ChildDTO> findChildrenBySponsorId(Long sponsorId) {
        List<Child> children = childRepository.findBySponsorId(sponsorId);
        return children.stream()
                .map(ChildDTO::toEntity)
                .collect(Collectors.toList());
    }


    @Transactional
    public SponsorDTO sponsorAddChild(Long sponsorId, Long childId) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.findById(sponsorId);
        Optional<Child> childOptional = childRepository.findById(childId);

        if(sponsorOptional.isPresent()){
            if(childOptional.isPresent()){
                Sponsor sponsor = sponsorOptional.get();
                Child child = childOptional.get();

                sponsor.setChild(child);
                child.setSponsor(sponsor);

                childRepository.save(child);
                sponsorRepository.save(sponsor);

                return SponsorDTO.toEntity(sponsor);
            }
        }

        return null;
    }

    @Transactional
    public SponsorDTO sponsorDeleteChild(Long sponsorId, Long childId) {
        Optional<Sponsor> sponsorOptional = sponsorRepository.findById(sponsorId);
        Optional<Child> childOptional = childRepository.findById(childId);

        if (sponsorOptional.isPresent() && childOptional.isPresent()) {
            Sponsor sponsor = sponsorOptional.get();
            Child child = childOptional.get();

            if (child.getSponsor() != null && child.getSponsor().getId().equals(sponsorId)) {
                child.setSponsor(null);
                sponsor.setChild(null);

                childRepository.save(child);
                sponsorRepository.save(sponsor);

                return SponsorDTO.toEntity(sponsor);
            }
        }

        return null;
    }
}
