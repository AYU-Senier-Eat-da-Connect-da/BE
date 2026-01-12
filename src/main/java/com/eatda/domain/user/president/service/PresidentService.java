package com.eatda.domain.user.president.service;

import com.eatda.domain.user.president.dto.PresidentDTO;
import com.eatda.domain.user.president.entity.President;
import com.eatda.domain.user.president.repository.PresidentRepository;
import com.eatda.global.exception.CustomException;
import com.eatda.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PresidentService {

    private final PresidentRepository presidentRepository;

    public PresidentDTO getPresidentInfo(Long presidentId) {
        President president = presidentRepository.findById(presidentId)
                .orElseThrow(() -> new CustomException(ErrorCode.PRESIDENT_NOT_FOUND));
        return PresidentDTO.from(president);
    }
}
