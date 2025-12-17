package org.example.wsd_final_team_project.dao;

import org.example.wsd_final_team_project.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentDAOImpl implements CommentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertComment(CommentVO vo) {
        String sql = "INSERT INTO Comment (post_id, user_id, comment_text) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, vo.getPostId(), vo.getUserId(), vo.getCommentText());
    }

    @Override
    public int deleteComment(int id) {
        String sql = "DELETE FROM Comment WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public List<CommentVO> getCommentsByPostId(int postId) {
        // Member 테이블과 조인하여 작성자의 nickname도 함께 가져옵니다.
        String sql = "SELECT c.*, m.nickname " +
                "FROM Comment c " +
                "JOIN Member m ON c.user_id = m.user_id " +
                "WHERE c.post_id = ? " +
                "ORDER BY c.created_at ASC";

        return jdbcTemplate.query(sql, new RowMapper<CommentVO>() {
            @Override
            public CommentVO mapRow(ResultSet rs, int rowNum) throws SQLException {
                CommentVO vo = new CommentVO();
                vo.setId(rs.getInt("id"));
                vo.setPostId(rs.getInt("post_id"));
                vo.setUserId(rs.getInt("user_id"));
                vo.setCommentText(rs.getString("comment_text"));
                vo.setCreatedAt(rs.getDate("created_at"));
                vo.setNickname(rs.getString("nickname")); // 조인된 닉네임 세팅
                return vo;
            }
        }, postId);
    }
}