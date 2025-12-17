package org.example.wsd_final_team_project.dao;

import org.example.wsd_final_team_project.vo.CommentVO;
import java.util.List;

public interface CommentDAO {
    // 댓글 쓰기
    int insertComment(CommentVO vo);

    // 댓글 삭제
    int deleteComment(int id);

    // 특정 게시글의 댓글 목록 가져오기
    List<CommentVO> getCommentsByPostId(int postId);
}