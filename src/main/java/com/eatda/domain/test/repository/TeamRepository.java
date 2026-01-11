package com.eatda.domain.test.repository;

import com.eatda.domain.test.entitiy.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {

    // fetch join - Inner Join : 쿼리 1번으로 Team + Member 한 번에 조회
    @Query("select distinct t from Team t join fetch t.memberList")
    List<Team> findAllWithInnerFetchJoin();

    // fetch join - Outer Join : 쿼리 1번으로 Team + Member 한 번에 조회
    @Query("select t from Team t left join fetch t.memberList")
    List<Team> findAllWithOuterFetchJoin();

}