package com.eatda.domain.user.child.repository;

import com.eatda.domain.user.child.entity.Child;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ChildRepository extends JpaRepository<Child, Long> {
    List<Child> findBySponsorId(Long sponsorId);
    Optional<Child> findByChildEmail(String childEmail);
}
