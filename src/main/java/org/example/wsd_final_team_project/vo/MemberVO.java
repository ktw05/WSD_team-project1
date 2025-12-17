package org.example.wsd_final_team_project.vo;

import lombok.Data;
import java.util.Date;

@Data
public class MemberVO {
    private int userId;          // user_id (PK, int)
    private String username;     // username (로그인 ID)
    private String password;     // password (암호)
    private String nickname;     // nickname (화면 표시 이름)
    private String email;        // email
    private Date createdAt;      // created_at
    private String role;        //관리자
}