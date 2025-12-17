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
    public String writeAction(BirthdayPostVO vo,
                              @RequestParam("file") MultipartFile file,
                              HttpServletRequest request) throws IOException {

        // 파일이 비어있지 않다면 업로드 처리
        if (!file.isEmpty()) {
            // 저장할 실제 경로 구하기 (webapp/resources/upload)
            String realPath = request.getSession().getServletContext().getRealPath("/resources/upload");
            File dir = new File(realPath);
            if (!dir.exists()) dir.mkdirs(); // 폴더 없으면 생성

            // 파일 이름 중복 방지를 위해 UUID 사용
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            file.transferTo(new File(realPath, filename));

            // DB에 파일 경로 저장 (VO에 세팅)
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

    // 5. 게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
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
}