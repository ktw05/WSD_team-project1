package org.example.wsdfinalteamproject.service;

import org.example.wsdfinalteamproject.domain.LoginRequestDTO;
import org.example.wsdfinalteamproject.domain.MemberVO;
import org.example.wsdfinalteamproject.domain.SignUpRequestDTO;
import org.example.wsdfinalteamproject.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

// 1. interface가 아니라 class로 변경해야 코드를 짤 수 있습니다.
// 2. @Service를 붙여야 스프링이 "아 이거 서비스구나" 하고 인식합니다.
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // 3. 생성자 주입: Repository(창고지기)를 데려오는 과정
    @Autowired
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입 로직
     */
    public MemberVO signUp(SignUpRequestDTO req) {
        MemberVO member = new MemberVO();
        member.setUserId(String.valueOf(req.getId()));
        member.setPassword(req.getPassword());
        member.setUserName(req.getName());

        member.setNickname(req.getNickname());
        member.setEmail(req.getEmail());

        memberRepository.save(member);
        return member;
    }

    /**
     * 로그인 로직
     */
    public MemberVO login(LoginRequestDTO req) {
        // 1. 아이디로 회원 조회
        // (이제 위에서 memberRepository를 선언했으므로 빨간줄이 안 뜹니다)
        MemberVO member = memberRepository.findById(req.getId());

        // 2. 회원이 없거나(null) 비밀번호가 틀리면 에러
        if (member == null) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        if (!member.getPassword().equals(req.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return member;
    }
}