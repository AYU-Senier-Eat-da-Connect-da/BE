package com.eatda.goodInfluenceStore.service;

import com.eatda.goodInfluenceStore.domain.GoodInfluence;
import com.eatda.goodInfluenceStore.repository.GoodInfluenceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoodInfluenceStoreService {

    private final GoodInfluenceRepository goodInfluenceRepository;

    @Transactional
    public void saveAllGoodInfluence(List<GoodInfluence> goodInfluenceList) {
        goodInfluenceRepository.saveAll(goodInfluenceList);
    }

    public List<GoodInfluence> getAllGoodInfluenceList() {
        return goodInfluenceRepository.findAll();
    }
}
