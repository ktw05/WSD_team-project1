package org.example.wsdfinalteamproject.controller;

import org.example.wsdfinalteamproject.domain.BirthdayVO; // VO 이름은 실제 프로젝트에 맞게 수정하세요
import org.example.wsdfinalteamproject.service.BirthdayService; // Service 이름 수정 필요
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/board") // 이 컨트롤러는 주소가 /board 로 시작하는 요청을 다 받습니다.
public class BirthdayController {

    // 만약 Service가 아직 없다면 이 부분은 주석 처리하고 테스트하세요.
    private final BirthdayService birthdayService;

    @Autowired
    public BirthdayController(BirthdayService birthdayService) {
        this.birthdayService = birthdayService;
    }

    /**
     * [GET] 생일 축하 게시글 목록 보기
     * URL: /board/list
     */
    @GetMapping("/list")
    public String list(Model model) {
        // DB에서 게시글 가져오기 (Service 연결)
        // List<BirthdayBoardVO> list = birthdayService.findAll();
        // model.addAttribute("boardList", list);

        return "boardList"; // /WEB-INF/views/boardList.jsp 화면을 보여줌
    }

    /**
     * [GET] 축하 글쓰기 페이지 이동
     * URL: /board/write
     */
    @GetMapping("/write")
    public String writePage(HttpSession session, Model model) {
        // 로그인 안 한 사람은 못 쓰게 막기 (보안)
        if (session.getAttribute("userId") == null) {
            return "redirect:/login";
        }
        return "boardWrite"; // /WEB-INF/views/boardWrite.jsp 화면을 보여줌
    }

    /**
     * [POST] 축하 글 저장하기 (Action)
     * URL: /board/writeAction
     */
    @PostMapping("/writeAction")
    public String writeProcess(BirthdayVO boardVO, HttpSession session) {
        // 작성자 ID 세션에서 꺼내서 넣기
        String writerId = (String) session.getAttribute("userId");
        // boardVO.setWriter(writerId);

        // DB에 저장
        // birthdayService.save(boardVO);

        return "redirect:/board/list"; // 저장 후 목록 페이지로 이동
    }
}