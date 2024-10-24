package com.eatda.goodInfluenceStore.service;

import com.eatda.goodInfluenceStore.domain.GoodInfluence;
import com.eatda.goodInfluenceStore.repository.GoodInfluenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodInfluenceStoreService {

    private final GoodInfluenceRepository goodInfluenceRepository;
    private static final Logger logger = LoggerFactory.getLogger(GoodInfluenceStoreService.class);

    @Transactional
    public void saveAllGoodInfluence(List<GoodInfluence> goodInfluenceList) {
        for (GoodInfluence goodInfluence : goodInfluenceList) {
            validateGoodInfluence(goodInfluence);
        }

        goodInfluenceRepository.saveAll(goodInfluenceList);
        logger.info("선한영향력 가게 정보 {}건 저장됨", goodInfluenceList.size());
    }

    private void validateGoodInfluence(GoodInfluence goodInfluence) {
        // 필수 필드 체크
        if (goodInfluence.getCMPNM_NM() == null || goodInfluence.getCMPNM_NM().isEmpty()) {
            logger.error("상호명은 필수입니다: {}", goodInfluence);
            throw new IllegalArgumentException("상호명은 필수입니다.");
        }
        if (goodInfluence.getINDUTYPE_NM() == null || goodInfluence.getINDUTYPE_NM().isEmpty()) {
            logger.error("업종명은 필수입니다: {}", goodInfluence);
            throw new IllegalArgumentException("업종명은 필수입니다.");
        }
    }
}
