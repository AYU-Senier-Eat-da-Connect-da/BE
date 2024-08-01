package com.eatda.president.controller;

import com.eatda.president.domain.PresidentBusinessRequest;
import com.eatda.president.domain.PresidentBusinessResponse;
import com.eatda.president.service.PresidentBusinessStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PresidentBusinessStatusController {

    private final PresidentBusinessStatusService presidentBusinessStatusService;

    @PostMapping("/api/president/businessNumberCheck")
    public ResponseEntity<PresidentBusinessResponse> getBusinessStatus(@RequestBody PresidentBusinessRequest request) {
        PresidentBusinessResponse response = presidentBusinessStatusService.getBusinessStatus(request);

        if (response != null && response.getData() != null && !response.getData().isEmpty()) {
            PresidentBusinessResponse.BusinessData businessData = response.getData().get(0);

            if ("부가가치세".contains(businessData.getRbf_tax_type()) || "고유번호".contains(businessData.getRbf_tax_type())) {
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
