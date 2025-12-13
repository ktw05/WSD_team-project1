package org.example.wsdfinalteamproject.controller;

import org.example.wsdfinalteamproject.domain.LoginRequestDTO;
import org.example.wsdfinalteamproject.domain.MemberVO;
import org.example.wsdfinalteamproject.domain.SignUpRequestDTO;
import org.example.wsdfinalteamproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * [GET] 메인 페이지
     */
    @GetMapping({"/", "/main"})
    public String home(HttpSession session, Model model) {
        // 1. 세션에서 닉네임 가져오기 (기존 "name" -> "nickname"으로 변경!)
        String nickname = (String) session.getAttribute("nickname");

        // 디버깅용 로그 (콘솔 확인용)
        System.out.println(">>> 메인 접근 시도. 세션 닉네임: " + nickname);

        // 2. 닉네임이 없으면(로그인 안 했으면) 로그인 페이지로 쫓아냄
        if (nickname == null) {
            return "redirect:/login";
        }

        // 3. JSP로 닉네임 전달
        model.addAttribute("nickname", nickname);

        return "index"; // /WEB-INF/views/index.jsp 로 이동
    }

    /**
     * [GET] 로그인 페이지 이동
     */
    @GetMapping("/login")
    public String loginPage() {
        System.out.println(">>> 로그인 페이지 이동");
        return "login";
    }

    /**
     * [GET] 회원가입 페이지 이동
     */
    @GetMapping("/signup")
    public String signupPage() {
        System.out.println(">>> 회원가입 페이지 이동");
        return "signup";
    }

    /**
     * [POST] 회원가입 처리
     */
    @PostMapping("/signupAction")
    public String signupProcess(SignUpRequestDTO req, Model model) {
        System.out.println(">>> 회원가입 요청 들어옴: " + req.getId() + " / " + req.getName());

        try {
            memberService.signUp(req);
            System.out.println(">>> 회원가입 성공!");
            return "redirect:/login";
        } catch (Exception e) {
            System.out.println(">>> 회원가입 실패(에러): " + e.getMessage());
            e.printStackTrace(); // 에러 원인 상세 출력

            model.addAttribute("errorMessage", "회원가입 실패: " + e.getMessage());
            return "signup";
        }
    }

    /**
     * [POST] 로그인 처리
     */
    @PostMapping("/loginAction")
    public String loginProcess(LoginRequestDTO req, HttpSession session, Model model) {
        System.out.println(">>> 로그인 요청 들어옴 ID: " + req.getId());

        try {
            MemberVO member = memberService.login(req);
            if (member == null) { /* ... */ }

            // 세션 저장 (이름 대신 닉네임 저장!)
            session.setAttribute("userId", member.getUserId());

            // ▼▼▼ userName 대신 nickname을 저장합니다 ▼▼▼
            session.setAttribute("nickname", member.getNickname());

            return "redirect:/main";
        } catch (Exception e) {
            System.out.println(">>> 로그인 에러 발생: " + e.getMessage());
            e.printStackTrace(); // 에러 원인 출력

            model.addAttribute("errorMessage", "아이디 또는 비밀번호를 확인해주세요.");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        System.out.println(">>> 로그아웃");
        session.invalidate();
        return "redirect:/login";
    }
}