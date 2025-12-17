package org.example.wsd_final_team_project.controller;

import org.example.wsd_final_team_project.dao.BirthdayPostDAO;
import org.example.wsd_final_team_project.dao.MemberDAO;
import org.example.wsd_final_team_project.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private MemberDAO memberDAO;

    @Autowired
    private BirthdayPostDAO birthdayPostDAO;

    // 1. 관리자 메인 페이지 (회원 목록 보여주기)
    @GetMapping("/main")
    public String adminMain(HttpSession session, Model model) {
        // 보안 체크: 관리자가 아니면 메인으로 쫓아냄
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/board/list";
        }

        // 전체 회원 목록 가져오기
        List<MemberVO> members = memberDAO.getAllMembers();
        model.addAttribute("members", members);

        return "admin"; // admin.jsp로 이동
    }

    // 2. 회원 강제 탈퇴
    @GetMapping("/deleteMember")
    public String deleteMember(@RequestParam("id") int userId, HttpSession session) {
        // 보안 체크
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null || !"ADMIN".equals(loginMember.getRole())) {
            return "redirect:/board/list";
        }

        memberDAO.deleteMember(userId);
        return "redirect:/admin/main"; // 삭제 후 다시 관리자 페이지로
    }

    // 3. 게시글 관리 (게시글 강제 삭제는 BoardController의 delete 재활용하거나 여기서 구현)
    // 관리자가 게시글을 삭제할 때도 BoardController의 /board/delete/{id}를 호출하면 되지만,
    // 권한 체크가 필요하므로 BoardController의 delete 메소드에 관리자 확인 로직을 추가하는 것이 좋습니다.
}