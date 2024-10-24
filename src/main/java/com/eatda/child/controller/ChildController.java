package com.eatda.child.controller;

import com.eatda.child.form.ChildDTO;
import com.eatda.child.service.ChildService;
import com.eatda.sponsor.form.SponsorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/child")
public class ChildController {

    private final ChildService childService;

    // 아동이 후원자 정보 확인
    @GetMapping("/{childId}/sponsor")
    public ResponseEntity<SponsorDTO> getSponsorForChild(@PathVariable Long childId) {
        SponsorDTO sponsorDTO = childService.findSponsorForChild(childId);
        if (sponsorDTO != null) {
            return new ResponseEntity<>(sponsorDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
