package com.eatda.president.repository;

import com.eatda.president.domain.President;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PresidentRepository extends JpaRepository<President, Long> {
}
