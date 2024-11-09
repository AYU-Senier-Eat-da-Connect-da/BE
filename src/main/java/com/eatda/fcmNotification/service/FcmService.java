package com.eatda.fcmNotification.service;

import com.eatda.fcmNotification.repository.FcmUserTokenRepository;
import com.eatda.fcmNotification.domain.FcmUserToken;
import com.google.firebase.messaging.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FcmService {

    private final FcmUserTokenRepository fcmUserTokenRepository;

    // FCM 토큰을 저장하는 메서드
    public void saveToken(String fcmToken, String userType) {
        FcmUserToken fcmUserToken = FcmUserToken.builder()
                .fcmToken(fcmToken)
                .userType(userType)
                .build();
        fcmUserTokenRepository.save(fcmUserToken);
    }

    // 후원자의 후원 알림 전송
    public void sendSponsorshipNotification(String childToken) {
        String messageBody =  "님이 후원을 하고싶대요";
        sendFcmMessage(childToken, "후원 알림", messageBody);
    }

    // 아동의 주문 알림 전송
    public void sendOrderNotification(String storeOwnerToken) {
        String messageBody = "주문이 들어왔어요";
        sendFcmMessage(storeOwnerToken, "주문 알림", messageBody);
    }

    // 가게사장의 확인 알림 전송
    public void sendOrderCheckNotification(String childToken) {
        String messageBody = "주문이 확인되었어요 조리가 완료될 때까지 조금만 기다려주세요";
        sendFcmMessage(childToken, "주문 확인", messageBody);
    }

    // 가게사장의 조리 완료 알림 전송
    public void sendOrderCompletedNotification(String childToken) {
        String messageBody = "조리가 완료되었어요";
        sendFcmMessage(childToken, "조리 완료", messageBody);
    }

    // FCM 메시지 전송 메서드
    // 데이터 메시지 전송 메서드 수정
    private void sendFcmMessage(String fcmToken, String title, String body) {
        Message message = Message.builder()
                .setToken(fcmToken)
                .putData("title", title)  // 데이터 필드에 제목 추가
                .putData("body", body)    // 데이터 필드에 내용 추가
                .putData("additionalInfo", "주문 번호 12345") // 필요에 따라 추가 정보
                .build();

        try {
            FirebaseMessaging.getInstance().send(message);
            log.info("메시지 전송 성공: " + body);
        } catch (FirebaseMessagingException e) {
            log.error("FCM 메시지 전송 실패 (Firebase Error): {}", e.getErrorCode(), e);
        } catch (Exception e) {
            log.error("FCM 메시지 전송 실패 (General Error): {}", e.getMessage(), e);
        }
    }

}