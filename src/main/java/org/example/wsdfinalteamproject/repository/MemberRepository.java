package org.example.wsdfinalteamproject.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.example.wsdfinalteamproject.domain.MemberVO;

@Mapper
public interface MemberRepository {

    // 1. 회원가입: 닉네임, 이메일 추가 저장
    @Insert("INSERT INTO member (user_id, password, user_name, nickname, email) VALUES (#{userId}, #{password}, #{userName}, #{nickname}, #{email})")
    void save(MemberVO member);

    // 2. 로그인: 닉네임, 이메일도 같이 가져오기
    @Select("SELECT user_id AS userId, password, user_name AS userName, nickname, email FROM member WHERE user_id = #{userId}")
    MemberVO findById(@Param("userId") String userId);
}