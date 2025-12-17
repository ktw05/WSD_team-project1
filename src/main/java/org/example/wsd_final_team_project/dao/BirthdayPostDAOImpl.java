package org.example.wsd_final_team_project.dao;

import org.example.wsd_final_team_project.vo.BirthdayPostVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class BirthdayPostDAOImpl implements BirthdayPostDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int insertPost(BirthdayPostVO vo) {
        String sql = "INSERT INTO BirthdayPost (group_name, birthday_date, birthday_img_url, birthday_person_name, celebration_text) VALUES (?, ?, ?, ?, ?)";
        return jdbcTemplate.update(sql, vo.getGroupName(), vo.getBirthdayDate(), vo.getBirthdayImgUrl(), vo.getBirthdayPersonName(), vo.getCelebrationText());
    }

    @Override
    public int deletePost(int id) {
        String sql = "DELETE FROM BirthdayPost WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    @Override
    public int updatePost(BirthdayPostVO vo) {
        String sql = "UPDATE BirthdayPost SET group_name=?, birthday_date=?, birthday_img_url=?, birthday_person_name=?, celebration_text=? WHERE id=?";
        return jdbcTemplate.update(sql, vo.getGroupName(), vo.getBirthdayDate(), vo.getBirthdayImgUrl(), vo.getBirthdayPersonName(), vo.getCelebrationText(), vo.getId());
    }

    @Override
    public BirthdayPostVO getPost(int id) {
        String sql = "SELECT * FROM BirthdayPost WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new PostRowMapper(), id);
    }

    @Override
    public List<BirthdayPostVO> getPostList() {
        String sql = "SELECT * FROM BirthdayPost ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, new PostRowMapper());
    }

    @Override
    public void increaseViewCount(int id) {
        String sql = "UPDATE BirthdayPost SET view_count = view_count + 1 WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    class PostRowMapper implements RowMapper<BirthdayPostVO> {
        @Override
        public BirthdayPostVO mapRow(ResultSet rs, int rowNum) throws SQLException {
            BirthdayPostVO vo = new BirthdayPostVO();
            vo.setId(rs.getInt("id"));
            vo.setGroupName(rs.getString("group_name"));
            vo.setBirthdayDate(rs.getDate("birthday_date"));
            vo.setBirthdayImgUrl(rs.getString("birthday_img_url"));
            vo.setBirthdayPersonName(rs.getString("birthday_person_name"));
            vo.setCelebrationText(rs.getString("celebration_text"));
            vo.setViewCount(rs.getInt("view_count"));
            vo.setCreatedAt(rs.getDate("created_at"));
            return vo;
        }
    }
}