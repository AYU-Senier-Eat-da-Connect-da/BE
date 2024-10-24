package com.eatda.goodInfluenceStore.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter@Setter
public class GoodInfluence {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String CMPNM_NM;    // 상호명
    private String INDUTYPE_NM; // 업종명
    private String SIGUN_NM;    // 시군명
    private String REFINE_ROADNM_ADDR;  // 정제도로명주소
    private String REFINE_LOTNO_ADDR;   // 정제지번주소
    private String DETAIL_ADDR; // 상세주소
    private String BSN_TM_NM;   // 영업시간
    private String REFINE_WGS84_LAT;    // 위도
    private String REFINE_WGS84_LOGT;   // 경도
}