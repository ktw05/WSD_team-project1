// service/MemberService.java

package org.example.wsdfinalteamproject.service;

import org.example.wsdfinalteamproject.domain.LoginRequestDTO;
import org.example.wsdfinalteamproject.domain.MemberVO;
import org.example.wsdfinalteamproject.domain.SignUpRequestDTO;

public interface MemberService {

    /**
     * 회원가입 로직을 처리합니다.
     * 비밀번호 암호화, 중복 ID/이메일 검사 등의 유효성 검사를 수행합니다.
     * @param signUpRequest 회원가입 요청 데이터
     * @return 성공적으로 가입된 MemberVO
     */
    MemberVO signUp(SignUpRequestDTO signUpRequest);

    /**
     * 로그인 로직을 처리합니다.
     * 사용자 ID 존재 여부 및 비밀번호 일치 여부를 확인합니다.
     * @param loginRequest 로그인 요청 데이터
     * @return 인증에 성공한 MemberVO (인증 실패 시 예외 발생)
     */
    MemberVO login(LoginRequestDTO loginRequest);

    /**
     * 사용자 ID(PK)로 회원 정보를 조회합니다.
     * @param userId 회원 ID
     * @return MemberVO
     */
    MemberVO getMemberById(int userId);
}