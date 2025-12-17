package org.example.wsd_final_team_project.dao;

import org.example.wsd_final_team_project.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class MemberDAOImpl implements MemberDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertMember(MemberVO vo) {
        String sql = "INSERT INTO Member (username, password, nickname, email) VALUES (?, ?, ?, ?)";
        return jdbcTemplate.update(sql, vo.getUsername(), vo.getPassword(), vo.getNickname(), vo.getEmail());
    }

    @Override
    public MemberVO getMemberByUsername(String username) {
        String sql = "SELECT * FROM Member WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, new MemberRowMapper(), username);
        } catch (Exception e) {
            return null; // 찾는 회원이 없으면 null 반환
        }
    }

    @Override
    public int checkNickname(String nickname) {
        String sql = "SELECT COUNT(*) FROM Member WHERE nickname = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, nickname);
    }

    // DB 결과를 VO 객체로 변환해주는 매퍼
    class MemberRowMapper implements RowMapper<MemberVO> {
        @Override
        public MemberVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            MemberVO vo = new MemberVO();
            vo.setUserId(rs.getInt("user_id"));
            vo.setUsername(rs.getString("username"));
            vo.setPassword(rs.getString("password"));
            vo.setNickname(rs.getString("nickname"));
            vo.setEmail(rs.getString("email"));
            vo.setCreatedAt(rs.getDate("created_at"));
            vo.setRole(rs.getString("role"));
            return vo;
        }
    }

    @Override
    public List<MemberVO> getAllMembers() {
        String sql = "SELECT * FROM Member ORDER BY user_id DESC";
        return jdbcTemplate.query(sql, new MemberRowMapper());
    }

    @Override
    public int deleteMember(int userId) {
        String sql = "DELETE FROM Member WHERE user_id = ?";
        return jdbcTemplate.update(sql, userId);
    }
}