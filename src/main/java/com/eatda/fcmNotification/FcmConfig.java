package com.eatda.fcmNotification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import java.io.IOException;

@Slf4j
@Configuration
public class FcmConfig {

    @PostConstruct
    public void initialize() {
        try {
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseOptions options = FirebaseOptions.builder()
                        .setCredentials(
                                GoogleCredentials.fromStream(new ClassPathResource("eatda-95c91-firebase-adminsdk-lz911-fae36ad568.json").getInputStream())
                        )
                        .build();
                FirebaseApp.initializeApp(options);
                log.info("FCM 설정 성공");
            }
        } catch (IOException exception) {
            log.error("FCM 연결 오류: {}", exception.getMessage(), exception);
        }
    }
}
