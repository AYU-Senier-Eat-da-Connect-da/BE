package com.eatda.domain.user.president.repository;

import com.eatda.domain.user.president.entity.President;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface PresidentRepository extends JpaRepository<President, Long> {
    Optional<President> findByPresidentEmail(String presidentEmail);
}
