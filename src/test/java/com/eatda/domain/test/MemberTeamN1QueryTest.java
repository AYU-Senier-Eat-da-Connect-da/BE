package com.eatda.domain.test;

import com.eatda.domain.test.entitiy.Member;
import com.eatda.domain.test.entitiy.Team;
import com.eatda.domain.test.repository.MemberRepository;
import com.eatda.domain.test.repository.TeamRepository;
import com.eatda.domain.test.service.TeamService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberTeamN1QueryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamService teamService;

    @Autowired
    private EntityManager em;

    private static final int TEAM_COUNT = 3;      // 팀 개수
    private static final int MEMBER_PER_TEAM = 4; // 팀당 멤버 개수

    @BeforeEach
    void setUp() {
        // 1. 테스트용 팀들 생성
        for (int i = 1; i <= TEAM_COUNT; i++) {
            Team team = new Team(i, "팀" + i);
            teamRepository.save(team);

            // 2. 각 팀에 멤버들 생성
            for (int j = 1; j <= MEMBER_PER_TEAM; j++) {
                Member member = Member.builder()
                        .nickname("멤버" + i + "-" + j)
                        .team(team)
                        .build();
                memberRepository.save(member);
            }
        }

        em.flush();
        em.clear();

        System.out.println("테스트 데이터 생성 완료!");
        System.out.println("- 팀 수: " + TEAM_COUNT);
        System.out.println("- 팀당 멤버 수: " + MEMBER_PER_TEAM);
        System.out.println("- 전체 멤버 수: " + (TEAM_COUNT * MEMBER_PER_TEAM));
    }

    @Test
    @DisplayName("N+1 문제 재현 : Team 조회 후 Member에 접근")
    void findAllTeam(){
        System.out.println("==========N+1 문제 발생 테스트==========");

        List<String> nicknames = teamService.findAllTeamMemberNicknames_basic();

        System.out.println("조회된 멤버 수 : " + nicknames.size());
    }

    @Test
    @DisplayName("Lazy로 조회")
    void findLazy(){

        List<Team> all = teamRepository.findAll();

        /**
         * select t1_0.id,t1_0.team_name from team t1_0
         */
    }

    @Test
    @DisplayName("Eager로 조회")
    void findEager(){

        List<Team> all = teamRepository.findAll();

        /**
         * select t1_0.id,t1_0.team_name from team t1_0
         * select ml1_0.team_id,ml1_0.id,ml1_0.nickname from member ml1_0 where ml1_0.team_id=?
         * select ml1_0.team_id,ml1_0.id,ml1_0.nickname from member ml1_0 where ml1_0.team_id=?
         * select ml1_0.team_id,ml1_0.id,ml1_0.nickname from member ml1_0 where ml1_0.team_id=?
         */
    }

    @Test
    @DisplayName("Eager + findById : left join 확인")
    void eagerLeftJoin(){
        Team team1= teamRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        // select t1_0.id,t1_0.team_name,ml1_0.team_id,ml1_0.id,ml1_0.nickname from team t1_0 left join member ml1_0 on t1_0.id=ml1_0.team_id where t1_0.id=?
    }

    @Test
    @DisplayName("Fetch Join : 쿼리 1번")
    void testFetchJoin(){
        System.out.println("==========Fetch Join 테스트==========");

        // 결과 : select distinct t1_0.id,ml1_0.team_id,ml1_0.id,ml1_0.nickname,t1_0.team_name from team t1_0 join member ml1_0 on t1_0.id=ml1_0.team_id
        List<String> nicknames1 = teamService.findAllTeamMemberNicknames_FetchJoinI_InnerJoin();

        // 결과 : select t1_0.id,ml1_0.team_id,ml1_0.id,ml1_0.nickname,t1_0.team_name from team t1_0 left join member ml1_0 on t1_0.id=ml1_0.team_id
        List<String> nicknames2 = teamService.findAllTeamMemberNicknames_FetchJoinI_OuterJoin();

    }

    @Test
    @DisplayName("EntityGraph 사용")
    void testEntityGraph(){
        System.out.println("==========EntityGraph 테스트==========");

        // 결과 : select t1_0.id,ml1_0.team_id,ml1_0.id,ml1_0.nickname,t1_0.team_name from team t1_0 left join member ml1_0 on t1_0.id=ml1_0.team_id
        List<String> nicknames = teamService.findAllTeamMemberNicknames_EntityGraph();
    }

    @Test
    @DisplayName("Batch Fetching으로 조회")
    void testBatchFetching(){
        System.out.println("==========Batch Fetching 테스트==========");

        /**
         * team = 3팀
         *
         * batchsize = 5
         * select t1_0.id,t1_0.team_name from team t1_0
         * select ml1_0.team_id,ml1_0.id,ml1_0.nickname from member ml1_0 where ml1_0.team_id in (?,?,?,?,?)
         *
         * batchsize = 2
         * select t1_0.id,t1_0.team_name from team t1_0
         * select ml1_0.team_id,ml1_0.id,ml1_0.nickname from member ml1_0 where ml1_0.team_id in (?,?)
         * select ml1_0.team_id,ml1_0.id,ml1_0.nickname from member ml1_0 where ml1_0.team_id=?
         */
        List<Team> teams = teamRepository.findAll();
    }

    @Test
    @DisplayName("Subselect Fetching으로 조회")
    void testSubselectFetching(){
        System.out.println("==========Subselect Fetching 테스트==========");

        /**
         * select t1_0.id,t1_0.team_name from team t1_0
         * select ml1_0.team_id,ml1_0.id,ml1_0.nickname from member ml1_0 where ml1_0.team_id in (select t1_0.id from team t1_0)
         */
        List<Team> teams = teamRepository.findAll();
    }

}
