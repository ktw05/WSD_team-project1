// service/MemberServiceImpl.java

package org.example.wsdfinalteamproject.service;

import org.example.wsdfinalteamproject.domain.LoginRequestDTO;
import org.example.wsdfinalteamproject.domain.MemberVO;
import org.example.wsdfinalteamproject.domain.SignUpRequestDTO;
import org.example.wsdfinalteamproject.mapper.MemberMapper;
import org.example.wsdfinalteamproject.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberServiceImpl implements MemberService {

    private final MemberMapper memberMapper;
    // 실제 Spring 프로젝트에서는 PasswordEncoder를 사용하지만, 여기서는 SecurityUtil로 대체합니다.
    private final SecurityUtil securityUtil;

    @Autowired
    public MemberServiceImpl(MemberMapper memberMapper, SecurityUtil securityUtil) {
        this.memberMapper = memberMapper;
        this.securityUtil = securityUtil;
    }

    @Override
    @Transactional // 트랜잭션 처리
    public MemberVO signUp(SignUpRequestDTO req) {
        // 1. 유효성 검사 (ID/이메일 중복 등은 Mapper/DB 제약 조건으로도 처리 가능)

        // 2. 비밀번호 암호화 (BCrypt 등 사용 가정)
        String encryptedPassword = securityUtil.encryptPassword(req.getPassword());

        // 3. DTO를 VO로 변환
        MemberVO newMember = new MemberVO();
        newMember.setUsername(req.getUsername());
        newMember.setPassword(encryptedPassword);
        newMember.setNickname(req.getNickname());
        newMember.setEmail(req.getEmail());

        // 4. DB 저장
        int result = memberMapper.insertMember(newMember);
        if (result == 0) {
            throw new RuntimeException("회원가입 DB 저장 실패");
        }

        // 저장이 완료된 객체 반환 (자동 생성된 userId 포함)
        return newMember;
    }

    @Override
    public MemberVO login(LoginRequestDTO req) {
        // 1. ID로 회원 정보 조회
        MemberVO memberVo = memberMapper.selectMemberByUsername(req.getUsername());

        if (memberVo == null) {
            // 사용자가 존재하지 않음
            throw new IllegalArgumentException("존재하지 않는 사용자 ID입니다.");
        }

        // 2. 비밀번호 일치 확인
        if (!securityUtil.matchesPassword(req.getPassword(), memberVo.getPassword())) {
            // 비밀번호 불일치
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // 3. 로그인 성공
        return memberVo;
    }

    @Override
    public MemberVO getMemberById(int userId) {
        return memberMapper.selectMemberById(userId);
    }
}