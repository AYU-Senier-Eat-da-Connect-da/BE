package com.eatda.sponsor.repository;

import com.eatda.sponsor.domain.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Optional<Sponsor> findBySponsorEmail(String sponsorEmail);
}