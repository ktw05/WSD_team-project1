package org.example.wsd_final_team_project.vo;

import lombok.Data;
import java.util.Date;

@Data
public class BirthdayPostVO {
    private int id;                  // id (PK)
    private String groupName;        // group_name (소속)
    private Date birthdayDate;       // birthday_date (생일)
    private String birthdayImgUrl;   // birthday_img_url (이미지 경로)
    private String birthdayPersonName; // birthday_person_name (생일자 이름)
    private String celebrationText;  // celebration_text (내용)
    private int viewCount;           // view_count (조회수)
    private Date createdAt;          // created_at
}