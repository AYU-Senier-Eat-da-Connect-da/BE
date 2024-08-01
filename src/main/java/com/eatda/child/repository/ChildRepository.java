package com.eatda.child.repository;

import com.eatda.child.domain.Child;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChildRepository extends JpaRepository {
    Child findOne(Long childId);
}
