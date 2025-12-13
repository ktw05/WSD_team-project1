package org.example.wsdfinalteamproject.domain;

public class LoginRequestDTO {

    // 1. 로그인 화면의 <input name="id"> 값을 받음
    private String id;

    // 2. 로그인 화면의 <input name="password"> 값을 받음
    private String password;

    private String name;
    private String nickname;
    private String email;



    // --- Getter & Setter (필수) ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}