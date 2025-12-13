package org.example.wsdfinalteamproject.domain;

public class MemberVO {
    private String userId;
    private String password;
    private String userName;

    private String nickname;
    private String email;

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    // [핵심] getUsername()이 아니라 getUserName() 이어야 합니다!
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}