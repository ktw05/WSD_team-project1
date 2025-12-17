package org.example.wsd_final_team_project.dao;

import org.example.wsd_final_team_project.vo.MemberVO;

public interface MemberDAO {
    // 회원가입
    int insertMember(MemberVO vo);

    // 로그인 체크 (username으로 회원 정보 가져오기)
    MemberVO getMemberByUsername(String username);

    // 닉네임 중복 체크 (선택 사항)
    int checkNickname(String nickname);
}