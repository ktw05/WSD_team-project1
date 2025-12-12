// domain/MemberVO.java

package org.example.wsdfinalteamproject.domain;

import java.time.LocalDateTime;

public class MemberVO {
    // DB 테이블 필드: user_id, username, password, nickname, email, created_at
    private int userId;
    private String username;
    private String password; // 암호화된 비밀번호
    private String nickname;
    private String email;
    private LocalDateTime createdAt;

    // Getter, Setter, toString, Constructors... (생략)
    // IDE를 사용하여 자동 생성하는 것을 권장합니다.

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}