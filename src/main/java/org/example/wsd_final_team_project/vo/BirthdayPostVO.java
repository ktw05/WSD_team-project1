package org.example.wsd_final_team_project.vo;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat; // 1. 이거 임포트 꼭!
import java.util.Date;

@Data
public class BirthdayPostVO {
    private int id;
    private String groupName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthdayDate;

    private String birthdayImgUrl;
    private String birthdayPersonName;
    private String celebrationText;
    private int viewCount;
    private Date createdAt;
}