package com.eatda.president.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PresidentBusinessResponse {

    private int request_cnt;
    private int match_cnt;
    private String status_code;
    private List<BusinessData> data;

    @Data
    public static class BusinessData {
        private String b_no;
        private String b_stt;
        private String b_stt_cd;
        private String tax_type;
        private String tax_type_cd;
        private String end_dt;
        private String utcc_yn;
        private String tax_type_change_dt;
        private String invoice_apply_dt;
        private String rbf_tax_type;
        private String rbf_tax_type_cd;
    }
}