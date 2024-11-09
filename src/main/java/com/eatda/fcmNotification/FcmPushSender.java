/*package com.eatda.fcmNotification;

import com.eatda.fcmNotification.form.FcmUserTokenRequestDTO;
import com.google.firebase.messaging.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FcmPushSender {
    public String sendPushNotification(FcmUserTokenRequestDTO fcmUserTokenRequestDTO) {
        Message message = Message.builder()
                .setToken(fcmUserTokenRequestDTO.token())
                .setNotification(Notification.builder()
                        .setTitle("알림 제목")
                        .setBody(fcmUserTokenRequestDTO.notificationType())
                        .build())
                .setAndroidConfig(
                        AndroidConfig.builder()
                                .setNotification(
                                        AndroidNotification.builder()
                                                .setTitle("알림 제목")
                                                .setBody(fcmUserTokenRequestDTO.notificationType())
                                                .setClickAction("push_click")
                                                .build()
                                ).build()
                ).build();
        try {
            return FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException exception) {
            log.error("Fcm 메시지 전송 실패 : {}", exception.getMessage());
            throw new RuntimeException(exception);
        }
    }
}
*/