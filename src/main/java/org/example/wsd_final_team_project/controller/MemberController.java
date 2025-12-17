package org.example.wsd_final_team_project.controller;

import org.example.wsd_final_team_project.dao.MemberDAO;
import org.example.wsd_final_team_project.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member") // 모든 주소 앞에 /member 가 붙습니다.
public class MemberController {

    @Autowired
    private MemberDAO memberDAO;

    // 1. 로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage() {
        return "login"; // /WEB-INF/views/login.jsp 로 이동
    }

    // 2. 로그인 처리 (DB 확인)
    @PostMapping("/loginAction")
    public String loginAction(String username, String password, HttpSession session, Model model) {
        MemberVO loginVO = memberDAO.getMemberByUsername(username);

        if (loginVO != null && loginVO.getPassword().equals(password)) {
            // 로그인 성공: 세션에 사용자 정보 저장
            session.setAttribute("loginMember", loginVO);
            return "redirect:/board/list"; // 메인 화면으로 이동
        } else {
            // 로그인 실패
            model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }

    // 3. 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // 세션 전체 삭제
        return "redirect:/member/login";
    }

    // 4. 회원가입 페이지 이동
    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    // 5. 회원가입 처리
    @PostMapping("/registerAction")
    public String registerAction(MemberVO vo) {
        // TODO: 비밀번호 암호화 로직이 필요하다면 여기서 처리
        memberDAO.insertMember(vo);
        return "redirect:/member/login"; // 가입 후 로그인 페이지로
    }
}