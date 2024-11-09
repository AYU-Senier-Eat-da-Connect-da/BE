package com.eatda.fcmNotification.controller;

import com.eatda.fcmNotification.form.FcmUserTokenRequestDTO;
import com.eatda.fcmNotification.service.FcmService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class FcmController {

    private final FcmService fcmService;

    // 현재 시간을 한국 시간대 (KST)로 변환
    ZonedDateTime koreaTime = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }


    // 자식 사용자 FCM 토큰 저장
    @PostMapping("/child/token")
    public ResponseEntity<String> registerChildToken(@RequestBody FcmUserTokenRequestDTO request) {
        // userType을 "child"로 지정
        fcmService.saveToken(request.getFcmToken(), "child");
        return ResponseEntity.ok("Child token saved successfully");
    }

    // 사장 사용자 FCM 토큰 저장
    @PostMapping("/president/token")
    public ResponseEntity<String> registerPresidentToken(@RequestBody FcmUserTokenRequestDTO request) {
        // userType을 "president"로 지정
        fcmService.saveToken(request.getFcmToken(), "president");
        return ResponseEntity.ok("President token saved successfully");
    }

    // 후원자 사용자 FCM 토큰 저장
    @PostMapping("/sponsor/token")
    public ResponseEntity<String> registerSponsorToken(@RequestBody FcmUserTokenRequestDTO request) {
        // userType을 "sponsor"로 지정
        fcmService.saveToken(request.getFcmToken(), "sponsor");
        return ResponseEntity.ok("Sponsor token saved successfully");
    }


    // 후원 알림 전송 (후원자가 아동에게 후원 알림 전송)
    @PostMapping("/sendSponsorshipNotification")
    public ResponseEntity<String> sendSponsorshipNotification(@RequestBody String childToken) {
        log.info("Received request to send sponsorship notification. ChildToken: {}", childToken);

        try {
            // 후원 알림을 아동에게 전송
            fcmService.sendSponsorshipNotification(childToken);
            log.info("Sponsorship notification sent successfully to token: {}", childToken);
            return ResponseEntity.ok("Sponsorship notification sent successfully");
        } catch (Exception e) {
            log.error("Failed to send sponsorship notification. Error: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send sponsorship notification");
        }
    }


    // 주문 알림 전송 (아동이 주문한 후, 가게 사장에게 알림 전송)
    @PostMapping("/sendOrderNotification")
    public ResponseEntity<String> sendOrderNotification(@RequestParam String storeOwnerToken) {
        // 주문 알림을 가게 사장에게 전송
        fcmService.sendOrderNotification(storeOwnerToken);
        return ResponseEntity.ok("Order notification sent successfully");
    }

    // 주문 확인 알림 전송 (가게 사장이 주문을 확인하면 아동에게 알림 전송)
    @PostMapping("/sendOrderCheckNotification")
    public ResponseEntity<String> sendOrderCheckNotification(@RequestParam String childToken) {
        // 주문 확인 알림을 아동에게 전송
        fcmService.sendOrderCheckNotification(childToken);
        return ResponseEntity.ok("Order check notification sent successfully");
    }

    // 조리 완료 알림 전송 (가게 사장이 조리를 완료하면 아동에게 알림 전송)
    @PostMapping("/sendOrderCompletedNotification")
    public ResponseEntity<String> sendOrderCompletedNotification(@RequestParam String childToken) {
        // 주문 완료 알림을 아동에게 전송
        fcmService.sendOrderCompletedNotification(childToken);
        return ResponseEntity.ok("Order completed notification sent successfully");
    }
}