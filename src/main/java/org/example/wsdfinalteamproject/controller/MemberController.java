// controller/MemberController.java

package org.example.wsdfinalteamproject.controller;

import org.example.wsdfinalteamproject.domain.LoginRequestDTO;
import org.example.wsdfinalteamproject.domain.MemberVO;
import org.example.wsdfinalteamproject.domain.SignUpRequestDTO;
import org.example.wsdfinalteamproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

// RestController는 @Controller와 @ResponseBody를 합친 것으로, JSON 응답을 자동으로 처리합니다.
@RestController
@RequestMapping("/auth")
public class MemberController {

    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * [POST] 회원가입 처리
     * URL: /auth/signup
     */
    @PostMapping("/signup")
    public ResponseEntity<Map<String, Object>> signup(@RequestBody SignUpRequestDTO req) {
        Map<String, Object> response = new HashMap<>();
        try {
            MemberVO member = memberService.signUp(req);
            response.put("success", true);
            response.put("message", "회원가입이 성공적으로 완료되었습니다.");
            // 보안을 위해 비밀번호는 응답에 포함하지 않습니다.
            response.put("nickname", member.getNickname());

            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
        } catch (IllegalArgumentException e) {
            // ID/이메일 중복 등의 비즈니스 예외 처리
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST); // 400 Bad Request
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류로 회원가입에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }

    /**
     * [POST] 로그인 처리
     * URL: /auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody LoginRequestDTO req, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            MemberVO member = memberService.login(req);

            // 로그인 성공 시 세션에 사용자 정보 저장 (인증/인가 구조의 기본)
            // JWT 토큰을 사용하는 경우 토큰을 생성하여 응답에 포함해야 합니다.
            session.setAttribute("loggedInUser", member.getUserId());
            session.setMaxInactiveInterval(3600); // 세션 만료 시간 설정 (1시간)

            response.put("success", true);
            response.put("message", "로그인 성공");
            response.put("userId", member.getUserId());
            response.put("nickname", member.getNickname());
            // TODO: 실제 JWT 구현 시, 여기에 토큰을 추가해야 합니다.

            return new ResponseEntity<>(response, HttpStatus.OK); // 200 OK
        } catch (IllegalArgumentException e) {
            // ID/비밀번호 불일치 예외 처리
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "서버 오류로 로그인에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // 500
        }
    }
}