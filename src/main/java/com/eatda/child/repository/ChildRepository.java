package com.eatda.child.repository;

import com.eatda.child.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findBySponsorId(Long sponsorId);
    Optional<Child> findByChildEmail(String childEmail);
}
