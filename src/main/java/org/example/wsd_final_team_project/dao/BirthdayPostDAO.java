package org.example.wsd_final_team_project.dao;

import org.example.wsd_final_team_project.vo.BirthdayPostVO;
import java.util.List;

public interface BirthdayPostDAO {
    // 게시글 작성
    int insertPost(BirthdayPostVO vo);

    // 게시글 삭제
    int deletePost(int id);

    // 게시글 수정
    int updatePost(BirthdayPostVO vo);

    // 게시글 하나 상세 보기
    BirthdayPostVO getPost(int id);

    // 전체 게시글 목록 가져오기
    List<BirthdayPostVO> getPostList();

    // 조회수 증가
    void increaseViewCount(int id);

    List<BirthdayPostVO> searchPosts(String keyword);
}