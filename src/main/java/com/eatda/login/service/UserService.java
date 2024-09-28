package com.eatda.login.service;

import com.eatda.login.security.JwtGenerator;
import com.eatda.login.form.JoinRequest;
import com.eatda.login.form.LoginRequest;
import com.eatda.president.domain.President;
import com.eatda.president.repository.PresidentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PresidentRepository presidentRepository;
    private final JwtGenerator jwtGenerator;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public void joinPresident(JoinRequest.PresidentJoinRequest joinRequest) {
        // 이메일 중복 체크
        if (presidentRepository.findByPresidentEmail(joinRequest.getPresidentEmail()).isPresent()) {
            throw new IllegalArgumentException("해당 Email은 사용할 수 없습니다.");
        }

        // 비밀번호 해시 처리
        String hashedPassword = passwordEncoder.encode(joinRequest.getPresidentPassword());

        // 새로운 President 객체 생성 및 저장
        President newPresident = President.builder()
                .businessNumber(joinRequest.getBusinessNumber())
                .presidentName(joinRequest.getPresidentName())
                .presidentEmail(joinRequest.getPresidentEmail())
                .presidentPassword(hashedPassword) // 해시 처리된 비밀번호 사용
                .presidentNumber(joinRequest.getPresidentNumber())
                .build();

        presidentRepository.save(newPresident);
    }

    public String loginPresident(LoginRequest loginRequest) {
        // Find the president by email
        President president = presidentRepository.findByPresidentEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // Check if the password matches
        if (!passwordEncoder.matches(loginRequest.getPassword(), president.getPresidentPassword())) {
            throw new IllegalArgumentException("비밀번호가 올바르지 않습니다.");
        }

        // Generate JWT token
        return jwtGenerator.generateToken(president.getId()).getAccessToken();

    }

}
