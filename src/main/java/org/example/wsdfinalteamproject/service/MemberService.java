package org.example.wsdfinalteamproject.service;

import org.example.wsdfinalteamproject.domain.LoginRequestDTO;
import org.example.wsdfinalteamproject.domain.MemberVO;
import org.example.wsdfinalteamproject.domain.SignUpRequestDTO;
import org.example.wsdfinalteamproject.mapper.MemberMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberMapper memberMapper;

    @Autowired
    public MemberService(MemberMapper memberMapper) {
        this.memberMapper = memberMapper;
    }

    /**
     * 회원가입
     */
    @Transactional
    public MemberVO signUp(SignUpRequestDTO req) {
        // 중복 ID 체크 (user_id 기준)
        if (memberMapper.selectMemberByUsername(req.getId()) != null) {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }

        MemberVO member = new MemberVO();
        member.setUserId(req.getId());      // PK
        member.setPassword(req.getPassword());
        member.setUserName(req.getName());
        member.setNickname(req.getNickname());
        member.setEmail(req.getEmail());

        int rows = memberMapper.insertMember(member);
        System.out.println(">>> insert rows = " + rows);

        if (rows != 1) {
            throw new IllegalStateException("회원가입 실패");
        }

        return member;
    }

    /**
     * 로그인
     */
    public MemberVO login(LoginRequestDTO req) {
        MemberVO member = memberMapper.selectMemberByUsername(req.getId());

        if (member == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        if (!member.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}
