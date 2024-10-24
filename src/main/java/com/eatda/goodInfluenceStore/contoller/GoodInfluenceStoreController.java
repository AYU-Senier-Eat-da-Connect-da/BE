package com.eatda.goodInfluenceStore.controller;

import com.eatda.goodInfluenceStore.domain.GoodInfluence;
import com.eatda.goodInfluenceStore.service.GoodInfluenceStoreService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class GoodInfluenceStoreController {

    private final GoodInfluenceStoreService goodInfluenceStoreService;
    private static final Logger logger = LoggerFactory.getLogger(GoodInfluenceStoreController.class);

    @GetMapping("/api/goodInfluenceStore")
    public String callGoodInfluenceStoreApi() {
        StringBuilder result = new StringBuilder();
        // API URL 하드코딩
        String urlStr = "https://openapi.gg.go.kr/GGGOODINFLSTOREST?KEY=c1d303fc6d9b40bca412e31828f322f5&Type=json&pIndex=1&pSize=1000";

        HttpURLConnection urlConnection = null;
        BufferedReader br = null;
        try {
            // API 요청 설정
            URL url = new URL(urlStr);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000); // 10초 연결 타임아웃
            urlConnection.setReadTimeout(10000); // 10초 읽기 타임아웃

            // 응답을 BufferedReader로 읽기
            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;
            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine).append("\n");
            }

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result.toString());
            JsonNode itemsNode = rootNode.path("GGGOODINFLSTOREST").get(1).path("row");
            logger.info("Items count: " + itemsNode.size());

            List<GoodInfluence> goodInfluenceList = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                GoodInfluence goodInfluence = new GoodInfluence();

                // 각 필드에 값 설정
                goodInfluence.setCMPNM_NM(itemNode.path("CMPNM_NM").asText());
                goodInfluence.setINDUTYPE_NM(itemNode.path("INDUTYPE_NM").asText());
                goodInfluence.setSIGUN_NM(itemNode.path("SIGUN_NM").asText());
                goodInfluence.setREFINE_ROADNM_ADDR(itemNode.path("REFINE_ROADNM_ADDR").asText());
                goodInfluence.setREFINE_LOTNO_ADDR(itemNode.path("REFINE_LOTNO_ADDR").asText());
                goodInfluence.setDETAIL_ADDR(itemNode.path("DETAIL_ADDR").asText());
                goodInfluence.setBSN_TM_NM(itemNode.path("BSN_TM_NM").asText());
                goodInfluence.setREFINE_WGS84_LAT(itemNode.path("REFINE_WGS84_LAT").asDouble());
                goodInfluence.setREFINE_WGS84_LOGT(itemNode.path("REFINE_WGS84_LOGT").asDouble());

                logger.info("파싱된 선한영향력 가게: " + goodInfluence);
                goodInfluenceList.add(goodInfluence);
            }

            // 데이터베이스에 저장
            goodInfluenceStoreService.saveAllGoodInfluence(goodInfluenceList);
            logger.info("선한영향력 가게 정보가 성공적으로 저장되었습니다.");

        } catch (IOException e) {
            logger.error("API 호출 중 오류가 발생했습니다: " + e.getMessage());
            return "API 호출 중 오류가 발생했습니다: " + e.getMessage();
        } finally {
            // 자원 정리
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    logger.error("BufferedReader 닫기 중 오류 발생: " + e.getMessage());
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return "데이터가 성공적으로 저장되었습니다.";
    }
}
