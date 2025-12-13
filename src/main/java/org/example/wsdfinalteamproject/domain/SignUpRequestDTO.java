package org.example.wsdfinalteamproject.domain;

public class SignUpRequestDTO {
    private int id;
    private String password;
    private String name;

    private String nickname;
    private String email;

    // --- Getter & Setter (이게 있어야 컨트롤러가 데이터를 넣고 뺍니다) ---

    public int getId() {
        return id;
    }
    public void setId(int id) {
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