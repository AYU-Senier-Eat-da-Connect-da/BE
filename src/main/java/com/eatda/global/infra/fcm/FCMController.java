package com.eatda.global.infra.fcm;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/notification")
public class FCMController {

    private final FCMNotificationService fcmNotificationService;

    @PostMapping
    public String sendNotification(@RequestParam Long userId, @RequestParam String userType,
                                   @RequestBody FCMNotificationRequestDTO requestDto) {
        return fcmNotificationService.sendNotificationToUser(userId, userType, requestDto);
    }
}
