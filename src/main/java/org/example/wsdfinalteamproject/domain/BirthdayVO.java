// domain/BirthdayVO.java

package org.example.wsdfinalteamproject.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class BirthdayVO {
    // DB 테이블 필드: id, group_name, birthday_date, birthday_img_url, birthday_person_name, celebration_text, created_at
    private int id;
    private String groupName;
    private LocalDate birthdayDate; // Date 타입
    private String birthdayImgUrl;
    private String birthdayPersonName;
    private String celebrationText;
    private LocalDateTime createdAt;
    // 조회수 (추가 기능)
    private int viewCount;

    // Getter, Setter, toString, Constructors... (생략)

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public LocalDate getBirthdayDate() {
        return birthdayDate;
    }

    public void setBirthdayDate(LocalDate birthdayDate) {
        this.birthdayDate = birthdayDate;
    }

    public String getBirthdayImgUrl() {
        return birthdayImgUrl;
    }

    public void setBirthdayImgUrl(String birthdayImgUrl) {
        this.birthdayImgUrl = birthdayImgUrl;
    }

    public String getBirthdayPersonName() {
        return birthdayPersonName;
    }

    public void setBirthdayPersonName(String birthdayPersonName) {
        this.birthdayPersonName = birthdayPersonName;
    }

    public String getCelebrationText() {
        return celebrationText;
    }

    public void setCelebrationText(String celebrationText) {
        this.celebrationText = celebrationText;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getViewCount() {
        return viewCount;
    }

    public void setViewCount(int viewCount) {
        this.viewCount = viewCount;
    }

    @Override
    public String toString() {
        return "BirthdayVO{" +
                "id=" + id +
                ", birthdayPersonName='" + birthdayPersonName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", birthdayDate=" + birthdayDate +
                '}';
    }
}