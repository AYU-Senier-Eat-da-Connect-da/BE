package com.eatda.login.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JoinRequest {

    @Data
    public static class PresidentJoinRequest {
        private String businessNumber;
        private String presidentName;
        private String presidentEmail;
        private String presidentPassword;
        private String presidentNumber;


    }
    @Data
    public static class ChildJoinRequest {
        private String childName;
        private String childEmail;
        private String childPassword;
        private String childNumber;
        private String childAddress;
    }
}
