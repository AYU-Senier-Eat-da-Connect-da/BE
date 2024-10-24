package com.eatda.goodInfluenceStore.contoller;

import com.eatda.goodInfluenceStore.domain.GoodInfluence;
import com.eatda.goodInfluenceStore.service.GoodInfluenceStoreService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequiredArgsConstructor
public class GoodInfluenceStoreController {

    private final GoodInfluenceStoreService goodInfluenceStoreService;

    private static final Logger logger = LoggerFactory.getLogger(GoodInfluenceStoreController.class);

    @GetMapping("/api/goodInfluenceStore")
    public String callGoodInfluenceStoreApi() throws IOException {
        StringBuilder result = new StringBuilder();
        String urlStr = "https://openapi.gg.go.kr/GGGOODINFLSTOREST?KEY=c1d303fc6d9b40bca412e31828f322f5&Type=json&pIndex=1&pSize=1000";
        URL url = new URL(urlStr);
        HttpURLConnection urlConnection = null;
        BufferedReader br = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setConnectTimeout(10000); // 10초로 연결 타임아웃 설정
            urlConnection.setReadTimeout(10000); // 10초로 읽기 타임아웃 설정

            br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "UTF-8"));
            String returnLine;

            while ((returnLine = br.readLine()) != null) {
                result.append(returnLine).append("\n\r");
            }

            // JSON 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result.toString());
            JsonNode itemsNode = rootNode.path("response").path("body").path("items");

            List<GoodInfluence> goodInfluenceList = new ArrayList<>();
            for (JsonNode itemNode : itemsNode) {
                GoodInfluence goodInfluence = new GoodInfluence();

                goodInfluence.setCMPNM_NM(itemNode.path("CMPNM_NM").asText());
                goodInfluence.setINDUTYPE_NM(itemNode.path("INDUTYPE_NM").asText());
                goodInfluence.setSIGUN_NM(itemNode.path("SIGUN_NM").asText());
                goodInfluence.setREFINE_ROADNM_ADDR(itemNode.path("REFINE_ROADNM_ADDR").asText());
                goodInfluence.setREFINE_LOTNO_ADDR(itemNode.path("REFINE_LOTNO_ADDR").asText());
                goodInfluence.setDETAIL_ADDR(itemNode.path("DETAIL_ADDR").asText());
                goodInfluence.setBSN_TM_NM(itemNode.path("BSN_TM_NM").asText());
                goodInfluence.setREFINE_WGS84_LAT(itemNode.path("REFINE_WGS84_LAT").asText());
                goodInfluence.setREFINE_WGS84_LOGT(itemNode.path("REFINE_WGS84_LOGT").asText());


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
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }

        return "데이터가 성공적으로 저장되었습니다.";
    }
}
