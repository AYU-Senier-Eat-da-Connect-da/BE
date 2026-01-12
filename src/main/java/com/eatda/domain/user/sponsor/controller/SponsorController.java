package com.eatda.domain.user.sponsor.controller;

import com.eatda.domain.user.child.dto.ChildDTO;
import com.eatda.domain.user.child.service.ChildService;
import com.eatda.domain.user.sponsor.dto.SponsorDTO;
import com.eatda.domain.user.sponsor.service.SponsorService;
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
    private final ChildService childService;

    @GetMapping("/all")
    public ResponseEntity<List<ChildDTO>> getAllChildren() {
        return ResponseEntity.ok(childService.getAllchildren());
    }

    @GetMapping("/{childId}")
    public ResponseEntity<ChildDTO> getChild(@PathVariable Long childId) {
        return ResponseEntity.ok(childService.getChildById(childId));
    }

    @GetMapping("/{sponsorId}/children")
    public ResponseEntity<List<ChildDTO>> findChild(@PathVariable Long sponsorId) {
        return ResponseEntity.ok(sponsorService.getChildrenBySponsorId(sponsorId));
    }

    @PostMapping("/{sponsorId}/add/{childId}")
    public ResponseEntity<SponsorDTO> addChildToSponsor(@PathVariable Long sponsorId, @PathVariable Long childId) {
        return ResponseEntity.ok(sponsorService.addChildToSponsor(sponsorId, childId));
    }

    @DeleteMapping("/{sponsorId}/delete/{childId}")
    public ResponseEntity<SponsorDTO> deleteChildFromSponsor(@PathVariable Long sponsorId, @PathVariable Long childId) {
        return ResponseEntity.ok(sponsorService.removeChildFromSponsor(sponsorId, childId));
    }

    @GetMapping("/update-amount/{sponsorId}")
    public ResponseEntity<String> updateAmount(@PathVariable Long sponsorId, @RequestParam int amount) {
        sponsorService.updateAmounts(sponsorId, amount);
        return ResponseEntity.ok("후원 결제 후 금액 업데이트 완료");
    }

    @GetMapping("/get/{sponsorId}")
    public ResponseEntity<SponsorDTO> getMyInformation(@PathVariable Long sponsorId) {
        return ResponseEntity.ok(sponsorService.getSponsorInfo(sponsorId));
    }
}
