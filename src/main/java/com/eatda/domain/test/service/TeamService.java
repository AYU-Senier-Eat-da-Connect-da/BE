package com.eatda.domain.test.service;

import com.eatda.domain.test.entitiy.Member;
import com.eatda.domain.test.entitiy.Team;
import com.eatda.domain.test.repository.TeamRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepository;

    // 1. N+1 문제 발생 코드
    public List<String> findAllTeamMemberNicknames_basic() {
        List<String> nicknames = new ArrayList<>();

        // 1. 모든 팀 조회 : 1번 쿼리 발생
        List<Team> teams = teamRepository.findAll();

        for (Team team : teams) {
            // 2. 팀의 멤버 조회 : N번 쿼리 발생
            List<Member> members = team.getMemberList();

            for (Member member : members) {
                nicknames.add(member.getNickname());
            }
        }

        return nicknames;
    }

    // 2. fetch Join - Inner Join
    public List<String> findAllTeamMemberNicknames_FetchJoinI_InnerJoin(){
        List<String> nicknames = new ArrayList<>();

        // 1. 쿼리 1번으로 Team + Member 한 번에 조회
        List<Team> teams = teamRepository.findAllWithInnerFetchJoin();

        for(Team team : teams){
            for(Member member : team.getMemberList()){
                nicknames.add(member.getNickname());
            }
        }

        return nicknames;
    }

    // 3. fetch Join - Outer Join
    public List<String> findAllTeamMemberNicknames_FetchJoinI_OuterJoin(){
        List<String> nicknames = new ArrayList<>();

        // 1. 쿼리 1번으로 Team + Member 한 번에 조회
        List<Team> teams = teamRepository.findAllWithOuterFetchJoin();

        for(Team team : teams){
            for(Member member : team.getMemberList()){
                nicknames.add(member.getNickname());
            }
        }

        return nicknames;
    }

    // 4. Entity Graph
    public List<String> findAllTeamMemberNicknames_EntityGraph(){
        List<String> nicknames = new ArrayList<>();

        List<Team> teams = teamRepository.findAllWithEntityGraph();

        for(Team team : teams){
            for(Member member : team.getMemberList()){
                nicknames.add(member.getNickname());
            }
        }

        return nicknames;
    }
}
