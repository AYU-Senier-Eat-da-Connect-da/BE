package com.eatda.domain.user.sponsor.repository;

import com.eatda.domain.user.sponsor.entity.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Optional<Sponsor> findBySponsorEmail(String sponsorEmail);
}
