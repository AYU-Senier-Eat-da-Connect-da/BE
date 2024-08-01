package com.eatda.menu.repository;

import com.eatda.menu.domain.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository {
    Menu findOne(Long menuId);
}
