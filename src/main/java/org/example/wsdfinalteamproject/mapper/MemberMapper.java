// mapper/MemberMapper.java

package org.example.wsdfinalteamproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.wsdfinalteamproject.domain.MemberVO;

@Mapper // MyBatis 매퍼임을 나타냅니다.
public interface MemberMapper {

    /**
     * 새로운 회원을 DB에 삽입합니다.
     * @param memberVo 회원 정보 (비밀번호는 암호화된 상태여야 합니다)
     * @return 삽입된 행의 수 (1)
     */
    int insertMember(MemberVO memberVo);

    /**
     * 사용자 이름(username, 로그인 ID)으로 회원 정보를 조회합니다.
     * @param username 로그인 ID
     * @return 해당 MemberVO 객체
     */
    MemberVO selectMemberByUsername(@Param("username") String username);

    /**
     * 사용자 ID(PK)로 회원 정보를 조회합니다.
     * @param userId 회원 테이블의 기본 키
     * @return 해당 MemberVO 객체
     */
    MemberVO selectMemberById(@Param("userId") int userId);

    /**
     * 닉네임이 존재하는지 확인합니다 (중복 체크)
     */
    int countMemberByNickname(@Param("nickname") String nickname);
}