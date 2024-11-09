package com.eatda.fcmNotification.repository;

import com.eatda.fcmNotification.domain.FcmUserToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FcmUserTokenRepository extends JpaRepository<FcmUserToken, Long> {
    // 사용자 타입에 맞는 FCM 토큰을 조회
    List<FcmUserToken> findByUserType(String userType);
}
