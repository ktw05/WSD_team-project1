package org.example.wsd_final_team_project.vo;

import lombok.Data;
import java.util.Date;

@Data
public class CommentVO {
    private int id;              // id (PK)
    private int postId;          // post_id (FK - 게시글 번호)
    private int userId;          // user_id (FK - 작성자 번호)
    private String commentText;  // comment_text (내용)
    private Date createdAt;      // created_at

    // DB 테이블엔 없지만, 화면에 '작성자 닉네임'을 보여주기 위해 추가한 필드
    private String nickname;
}