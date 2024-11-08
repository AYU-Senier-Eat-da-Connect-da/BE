package com.eatda.president.service;

import com.eatda.president.dto.PresidentBusinessRequest;
import com.eatda.president.dto.PresidentBusinessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;


@Service
@RequiredArgsConstructor
public class PresidentBusinessStatusService {
/*
    @Value("${baseUrl}")
    private String baseUrl;

    @Value("${serviceKey}")
    private String serviceKey;

    public PresidentBusinessResponse getBusinessStatus(PresidentBusinessRequest request) {
        String apiUrl = baseUrl + "serviceKey=" + serviceKey;

        try {
            URI uri = new URI(apiUrl);

            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<PresidentBusinessRequest> entity = new HttpEntity<>(request, headers);

            ResponseEntity<PresidentBusinessResponse> response = restTemplate.exchange(uri, HttpMethod.POST, entity, PresidentBusinessResponse.class);

            return response.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

 */
}
