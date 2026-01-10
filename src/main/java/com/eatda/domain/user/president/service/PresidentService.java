package com.eatda.domain.user.president.service;

import com.eatda.domain.user.president.dto.PresidentDTO;
import com.eatda.domain.user.president.entity.President;
import com.eatda.domain.user.president.repository.PresidentRepository;
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
