package com.eatda.sponsor.repository;

import com.eatda.sponsor.domain.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
}
