package com.eatda.global.infra.fcm;

import com.eatda.domain.user.child.repository.ChildRepository;
import com.eatda.domain.user.president.repository.PresidentRepository;
import com.eatda.domain.user.sponsor.repository.SponsorRepository;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class FCMNotificationService {

    private final ChildRepository childRepository;
    private final PresidentRepository presidentRepository;
    private final SponsorRepository sponsorRepository;
    private final FirebaseMessaging firebaseMessaging;

    public String sendNotificationToUser(Long userId, String userType, FCMNotificationRequestDTO requestDto) {
        NotifiableUser user = getUserByType(userId, userType);

        if (user == null) {
            return "해당 유저가 존재하지 않습니다. userId=" + userId;
        }

        if (user.getFcmToken() == null) {
            return "FirebaseToken이 존재하지 않습니다. userId=" + userId;
        }

        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(user.getFcmToken())
                .setNotification(notification)
                .build();

        try {
            firebaseMessaging.send(message);
            return "알림을 성공적으로 전송했습니다. userId=" + userId;
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
            return "알림 전송에 실패했습니다. userId=" + userId;
        }
    }

    private NotifiableUser getUserByType(Long userId, String userType) {
        return switch (userType.toUpperCase()) {
            case "CHILD" -> childRepository.findById(userId).orElse(null);
            case "PRESIDENT" -> presidentRepository.findById(userId).orElse(null);
            case "SPONSOR" -> sponsorRepository.findById(userId).orElse(null);
            default -> null;
        };
    }
}
