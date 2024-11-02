package com.eatda.sponsor.controller;

import com.eatda.child.form.ChildDTO;
import com.eatda.child.service.ChildService;
import com.eatda.sponsor.form.SponsorDTO;
import com.eatda.sponsor.service.SponsorService;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.connector.Response;
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

    // 후원자가 아동 전체 검색
    @GetMapping("/all")
    public ResponseEntity<List<ChildDTO>> getAllChildren(){
        List<ChildDTO> children = childService.getAllchildren();
        if(children!=null){
            return new ResponseEntity<>(children, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 후원자가 아동 선택 시 단일 리스트
    @GetMapping("/{childId}")
    public ResponseEntity<ChildDTO> getChild(@PathVariable Long childId){
        ChildDTO child = childService.getChildById(childId);
        if(child != null){
            return new ResponseEntity<>(child, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 후원 추가한 아동 검색
    @GetMapping("/{sponsorId}/children")
    public ResponseEntity<List<ChildDTO>> findChild(@PathVariable Long sponsorId){
        List<ChildDTO> children = sponsorService.findChildrenBySponsorId(sponsorId);
        return ResponseEntity.ok(children);
    }

    // 후원자가 아동 추가
    @PostMapping("/{sponsorId}/add/{childId}")
    public ResponseEntity<SponsorDTO> addChildToSponsor(@PathVariable Long sponsorId, @PathVariable Long childId) {
        SponsorDTO sponsorDTO = sponsorService.sponsorAddChild(sponsorId, childId);
        return new ResponseEntity<>(sponsorDTO, HttpStatus.OK);
    }

    // 후원자가 아동 삭제

    @DeleteMapping("/{sponsorId}/delete/{childId}")
    public ResponseEntity<SponsorDTO> deleteChildFromSponsor(@PathVariable Long sponsorId, @PathVariable Long childId) {
        SponsorDTO sponsorDTO = sponsorService.sponsorDeleteChild(sponsorId, childId);
        if (sponsorDTO != null) {
            return new ResponseEntity<>(sponsorDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 후원 확인 후 금액 추가
    @GetMapping("/update-amount/{sponsorId}")
    public ResponseEntity<String> updateAmount(@PathVariable Long sponsorId, @RequestParam int amount) {
        sponsorService.updateAmounts(sponsorId, amount);
        return ResponseEntity.ok("후원 결제 후 금액 업데이트 완료");
    }

    // 후원자 내 정보 가져오기
    @GetMapping("/get/{sponsorId}")
    public ResponseEntity<SponsorDTO> getMyInformation(@PathVariable Long sponsorId){
        SponsorDTO sponsorDTO = sponsorService.getSponsorInfo(sponsorId);
        if (sponsorDTO != null) {
            return new ResponseEntity<>(sponsorDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
