package com.eatda.domain.user.child.controller;

import com.eatda.domain.user.child.dto.ChildDTO;
import com.eatda.domain.user.child.service.ChildService;
import com.eatda.domain.user.sponsor.dto.SponsorDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/child")
public class ChildController {

    private final ChildService childService;

    @GetMapping("/{childId}/sponsor")
    public ResponseEntity<SponsorDTO> getSponsorForChild(@PathVariable Long childId) {
        return ResponseEntity.ok(childService.findSponsorForChild(childId));
    }

    @GetMapping("/get/{childId}")
    public ResponseEntity<ChildDTO> getMyInformation(@PathVariable Long childId) {
        return ResponseEntity.ok(childService.getChildInfo(childId));
    }

    @GetMapping("/update-amount/{childId}")
    public ResponseEntity<String> updateAmount(@PathVariable Long childId, @RequestParam int amount) {
        childService.updateAmounts(childId, amount);
        return ResponseEntity.ok("아동 충전 완료");
    }
}
