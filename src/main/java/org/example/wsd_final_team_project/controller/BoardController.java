package org.example.wsd_final_team_project.controller;

import org.example.wsd_final_team_project.dao.BirthdayPostDAO;
import org.example.wsd_final_team_project.dao.CommentDAO;
import org.example.wsd_final_team_project.vo.BirthdayPostVO;
import org.example.wsd_final_team_project.vo.CommentVO;
import org.example.wsd_final_team_project.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BirthdayPostDAO birthdayPostDAO;

    @Autowired
    private CommentDAO commentDAO;

    // 1. 전체 게시글 목록 (메인 화면)
    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "keyword", required = false) String keyword) {
        List<BirthdayPostVO> list;

        // 검색어가 있으면 검색(searchPosts), 없으면 전체보기(getPostList)
        if (keyword != null && !keyword.isEmpty()) {
            list = birthdayPostDAO.searchPosts(keyword);
        } else {
            list = birthdayPostDAO.getPostList();
        }

        model.addAttribute("posts", list);
        return "list"; // list.jsp
    }

    // 2. 글쓰기 페이지 이동 (로그인 한 사람만)
    @GetMapping("/write")
    public String writePage(HttpSession session) {
        if (session.getAttribute("loginMember") == null) {
            return "redirect:/member/login";
        }
        return "write"; // write.jsp
    }

    // 3. 글쓰기 처리 (파일 업로드 포함)
    @PostMapping("/writeAction")
    public String writeAction(
            BirthdayPostVO vo,
            @RequestParam("file") MultipartFile file,
            HttpServletRequest request,
            HttpSession session
    ) throws IOException {

        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        // ✅ 작성자 설정
        vo.setUserId(loginMember.getUserId());

        // 파일 업로드
        if (!file.isEmpty()) {
            String realPath = request.getSession().getServletContext().getRealPath("/resources/upload");
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs();

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            file.transferTo(new File(realPath, filename));
            vo.setBirthdayImgUrl(filename);
        }

        birthdayPostDAO.insertPost(vo);
        return "redirect:/board/list";
    }


    // 4. 게시글 상세 보기 (+ 댓글 목록)
    @GetMapping("/view/{id}")
    public String view(@PathVariable("id") int id, Model model) {
        // 조회수 증가
        birthdayPostDAO.increaseViewCount(id);

        // 게시글 정보 가져오기
        BirthdayPostVO post = birthdayPostDAO.getPost(id);
        model.addAttribute("post", post);

        // 댓글 목록 가져오기
        List<CommentVO> comments = commentDAO.getCommentsByPostId(id);
        model.addAttribute("comments", comments);

        return "view";
    }

    //  공통 권한 체크 함수(컨트롤러 내부 private 메서드로)
    private boolean canEditOrDelete(MemberVO loginMember, int postId) {
        if (loginMember == null) return false;
        if ("ADMIN".equals(loginMember.getRole())) return true;

        int ownerId = birthdayPostDAO.getPostOwnerId(postId);
        return ownerId == loginMember.getUserId();
    }

    // 5. 게시글 삭제 (권한 체크 포함)
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpSession session) {

        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) {
            return "redirect:/member/login";
        }

        // 1. 글 작성자 userId 가져오기
        Integer postOwnerId = birthdayPostDAO.getPostOwnerId(id);
        if (postOwnerId == null) {
            return "redirect:/board/list";
        }

        // 2. 권한 체크
        boolean isAdmin = "ADMIN".equals(loginMember.getRole());
        boolean isOwner = loginMember.getUserId() == postOwnerId;

        if (!isAdmin && !isOwner) {
            // 권한 없음
            return "redirect:/board/list";
        }

        // 3. 삭제 실행
        birthdayPostDAO.deletePost(id);
        return "redirect:/board/list";
    }


    // 6. 댓글 작성
    @PostMapping("/addComment")
    public String addComment(CommentVO vo, HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember != null) {
            vo.setUserId(loginMember.getUserId()); // 현재 로그인한 사용자 ID 설정
            commentDAO.insertComment(vo);
        }
        return "redirect:/board/view/" + vo.getPostId();
    }

    // 7. 댓글 삭제
    @GetMapping("/deleteComment")
    public String deleteComment(int id, int postId) {
        commentDAO.deleteComment(id);
        return "redirect:/board/view/" + postId;
    }

    //  8. 수정 페이지 이동
    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable("id") int id, Model model, HttpSession session) {
        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) return "redirect:/member/login";

        if (!canEditOrDelete(loginMember, id)) {
            return "redirect:/board/view/" + id;
        }

        BirthdayPostVO post = birthdayPostDAO.getPost(id);
        model.addAttribute("post", post);
        return "edit"; // edit.jsp
    }

    // 9. 수정 처리 (파일: 새 파일 없으면 기존 유지)
    @PostMapping("/editAction")
    public String editAction(
            BirthdayPostVO vo,
            @RequestParam(value = "file", required = false) MultipartFile file,
            HttpServletRequest request,
            HttpSession session
    ) throws IOException {

        MemberVO loginMember = (MemberVO) session.getAttribute("loginMember");
        if (loginMember == null) return "redirect:/member/login";

        int id = vo.getId();
        if (!canEditOrDelete(loginMember, id)) {
            return "redirect:/board/view/" + id;
        }

        // 기존 글 정보 가져오기(기존 이미지 유지 위해)
        BirthdayPostVO oldPost = birthdayPostDAO.getPost(id);
        if (oldPost == null) return "redirect:/board/list";

        // 파일 업로드: 새 파일 있으면 교체, 없으면 기존 유지
        if (file != null && !file.isEmpty()) {
            String realPath = request.getSession().getServletContext().getRealPath("/resources/upload");
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs();

            String filename = UUID.randomUUID() + "_" + file.getOriginalFilename();
            file.transferTo(new File(realPath, filename));
            vo.setBirthdayImgUrl(filename);

            // (선택) 기존 파일 삭제하고 싶으면 여기서 oldPost.getBirthdayImgUrl() 삭제 처리 가능
        } else {
            vo.setBirthdayImgUrl(oldPost.getBirthdayImgUrl());
        }

        // 작성자는 수정 시 바뀌면 안 되므로 유지
        vo.setUserId(oldPost.getUserId());

        birthdayPostDAO.updatePost(vo);
        return "redirect:/board/view/" + id;
    }
}