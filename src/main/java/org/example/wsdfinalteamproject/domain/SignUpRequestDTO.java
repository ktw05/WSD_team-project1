// domain/SignUpRequestDTO.java (클라이언트로부터 받을 데이터)

package org.example.wsdfinalteamproject.domain;

// VO와 달리, 클라이언트가 보내는 데이터만 포함합니다.
public class SignUpRequestDTO {
    private String username;
    private String password;
    private String nickname;
    private String email;

    // Getter, Setter, toString, Constructors... (생략)

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

    @Override
    public String toString() {
        return "SignUpRequestDTO{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}