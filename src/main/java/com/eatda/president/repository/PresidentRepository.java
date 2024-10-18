package com.eatda.president.repository;

import com.eatda.president.domain.President;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PresidentRepository extends JpaRepository<President, Long> {
    // 예외 발생 < 존재하지 않는 사용자 처리(반환 유형에 Optional 사용)
    Optional<President> findByPresidentEmail(String presidentEmail);
}
