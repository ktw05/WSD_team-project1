// src/main/java/org/example/wsdproject/util/SecurityUtil.java

package org.example.wsdfinalteamproject.util; // <-- 패키지 이름 확인

import org.springframework.stereotype.Component;

/**
 * 비밀번호 암호화 및 검증 유틸리티 클래스입니다.
 * 실제 서비스에서는 Spring Security의 BCryptPasswordEncoder 사용을 권장합니다.
 */
@Component // Spring 빈으로 등록하여 Service에서 @Autowired로 주입받을 수 있게 합니다.
public class SecurityUtil {

    // --- 실제 프로젝트에서는 BCryptPasswordEncoder를 필드로 주입받아 사용합니다. ---

    /**
     * 비밀번호를 암호화하여 반환합니다. (BCrypt 해싱을 가정)
     * @param rawPassword 평문 비밀번호
     * @return 암호화된 비밀번호 문자열
     */
    public String encryptPassword(String rawPassword) {
        // TODO: 실제 BCrypt 해싱 로직을 여기에 구현해야 합니다.
        // 현재는 임시로 [BCRYPT] 접두사를 붙여 암호화된 것처럼 처리합니다.
        System.out.println("DEBUG: Password Encrypting...");
        return "[BCRYPT]" + rawPassword;
    }

    /**
     * 평문 비밀번호와 암호화된 비밀번호를 비교합니다.
     * @param rawPassword 평문 비밀번호 (로그인 시 사용자 입력)
     * @param encryptedPassword 저장된 암호화된 비밀번호
     * @return 일치 여부
     */
    public boolean matchesPassword(String rawPassword, String encryptedPassword) {
        // TODO: 실제 BCrypt 비교 로직을 여기에 구현해야 합니다.
        // 현재는 임시로 [BCRYPT] 접두사가 붙어있는지만 확인하고 비교합니다.
        System.out.println("DEBUG: Password Matching...");
        if (encryptedPassword == null || !encryptedPassword.startsWith("[BCRYPT]")) {
            return false; // 암호화 형식이 다르면 실패
        }
        String storedRawPassword = encryptedPassword.substring("[BCRYPT]".length());
        return rawPassword.equals(storedRawPassword);
    }
}