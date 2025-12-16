package org.example.wsdfinalteamproject.domain;

public class SignUpRequestDTO {

    private String id;      // ★ userId(로그인 ID)와 동일
    private String password;
    private String name;
    private String nickname;
    private String email;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getNickname() { return nickname; }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
