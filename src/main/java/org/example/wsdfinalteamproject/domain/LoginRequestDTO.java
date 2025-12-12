// domain/LoginRequestDTO.java (클라이언트로부터 받을 데이터)

package org.example.wsdfinalteamproject.domain;

// 클라이언트가 로그인 시 보내는 ID와 비밀번호만 포함합니다.
public class LoginRequestDTO {
    private String username;
    private String password;

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

    @Override
    public String toString() {
        return "LoginRequestDTO{" +
                "username='" + username + '\'' +
                '}'; // 보안상 password는 출력하지 않습니다.
    }
}