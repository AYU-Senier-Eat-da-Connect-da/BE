package com.eatda.president.service;

import com.eatda.president.domain.President;
import com.eatda.president.dto.PresidentDTO;
import com.eatda.president.repository.PresidentRepository;
import com.eatda.sponsor.domain.Sponsor;
import com.eatda.sponsor.form.SponsorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PresidentService {

    private final PresidentRepository presidentRepository;

    public PresidentDTO getPresidentInfo(Long presidentId) {
        Optional<President> presidentOptional = presidentRepository.findById(presidentId);
        if (presidentOptional.isPresent()) {
            President president = presidentOptional.get();

            return PresidentDTO.builder()
                    .id(president.getId())
                    .presidentEmail(president.getPresidentEmail())
                    .presidentNumber(president.getPresidentNumber())
                    .presidentName(president.getPresidentName())
                    .businessNumber(Integer.parseInt(president.getBusinessNumber()))
                    .build();
        } else {
            throw new RuntimeException("President not found");
        }
    }
}
