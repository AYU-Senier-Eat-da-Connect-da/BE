package com.eatda.sponsor.repository;

import com.eatda.sponsor.domain.Sponsor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SponsorRepository extends JpaRepository<Sponsor, Long> {
    Optional<Sponsor> findBySponsorEmail(String sponsorEmail);
}
