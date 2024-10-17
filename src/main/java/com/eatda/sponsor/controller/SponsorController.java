package com.eatda.sponsor.controller;

import com.eatda.child.form.ChildDTO;
import com.eatda.sponsor.form.SponsorDTO;
import com.eatda.sponsor.service.SponsorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sponsor")
public class SponsorController {

    private final SponsorService sponsorService;

    @GetMapping("/{sponsorId}/children")
    public ResponseEntity<List<ChildDTO>> findChild(@PathVariable Long sponsorId){
        List<ChildDTO> children = sponsorService.findChildrenBySponsorId(sponsorId);
        return ResponseEntity.ok(children);
    }

    @PostMapping("/{sponsorId}/add/{childId}")
    public ResponseEntity<SponsorDTO> addChildToSponsor(@PathVariable Long sponsorId, @PathVariable Long childId) {
        SponsorDTO sponsorDTO = sponsorService.sponsorAddChild(sponsorId, childId);
        return new ResponseEntity<>(sponsorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{sponsorId}/delete/{childId}")
    public ResponseEntity<SponsorDTO> deleteChildFromSponsor(@PathVariable Long sponsorId, @PathVariable Long childId) {
        SponsorDTO sponsorDTO = sponsorService.sponsorDeleteChild(sponsorId, childId);
        if (sponsorDTO != null) {
            return new ResponseEntity<>(sponsorDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
