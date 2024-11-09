package com.eatda.fcmNotification.form;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FcmUserTokenRequestDTO {
    private String fcmToken;
    private String userType;
}
