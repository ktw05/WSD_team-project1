// controller/BirthdayController.java

package org.example.wsdfinalteamproject.controller;

import org.example.wsdfinalteamproject.domain.BirthdayVO;
import org.example.wsdfinalteamproject.domain.MessageVO;
import org.example.wsdfinalteamproject.service.BirthdayService;
import org.example.wsdfinalteamproject.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/birthdays")
public class BirthdayController {

    private final BirthdayService birthdayService;
    private final MemberService memberService; // 사용자 정보를 얻기 위해 주입

    @Autowired
    public BirthdayController(BirthdayService birthdayService, MemberService memberService) {
        this.birthdayService = birthdayService;
        this.memberService = memberService;
    }

    // --- 1. 게시글 목록 조회 및 검색 (Read - List) ---
    /**
     * [GET] 생일 보드 목록 조회 (검색/필터/정렬 지원)
     * URL: /birthdays?search={keyword}&sort={criteria}
     */
    @GetMapping
    public ResponseEntity<Map<String, Object>> getBirthdayList(
            @RequestParam(value = "search", required = false) String searchKeyword,
            @RequestParam(value = "sort", defaultValue = "latest") String sortCriteria) {

        Map<String, Object> response = new HashMap<>();
        try {
            List<BirthdayVO> boards = birthdayService.getBoardList(searchKeyword, sortCriteria);
            response.put("success", true);
            response.put("boards", boards);
            response.put("count", boards.size());
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "게시물 목록 조회에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- 2. 특정 게시글 상세 조회 (Read - Detail) ---
    /**
     * [GET] 특정 생일 보드 상세 조회
     * URL: /birthdays/{boardId}
     */
    @GetMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> getBirthdayDetail(@PathVariable int boardId) {
        Map<String, Object> response = new HashMap<>();
        try {
            BirthdayVO board = birthdayService.getBoardDetail(boardId);
            response.put("success", true);
            response.put("board", board);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND); // 404 Not Found
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "게시물 상세 조회에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- 3. 게시글 생성 (Create) ---
    /**
     * [POST] 생일 보드 생성(생일자 등록)
     * URL: /birthdays
     * 참고: 파일 업로드가 있으므로 MultipartFile을 사용합니다.
     */
    @PostMapping
    public ResponseEntity<Map<String, Object>> createBirthdayBoard(
            @RequestParam("groupName") String groupName,
            @RequestParam("birthdayDate") String birthdayDate, // YYYY-MM-DD 형태로 받습니다.
            @RequestParam("birthdayPersonName") String birthdayPersonName,
            @RequestParam("celebrationText") String celebrationText,
            @RequestParam("imageFile") MultipartFile imageFile,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        Integer loggedInUserId = (Integer) session.getAttribute("loggedInUser");

        if (loggedInUserId == null) {
            response.put("success", false);
            response.put("message", "로그인 후 보드를 생성할 수 있습니다.");
            return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
        }

        try {
            BirthdayVO newBoard = new BirthdayVO();
            newBoard.setGroupName(groupName);
            newBoard.setBirthdayDate(LocalDate.parse(birthdayDate)); // String을 LocalDate로 변환
            newBoard.setBirthdayPersonName(birthdayPersonName);
            newBoard.setCelebrationText(celebrationText);

            // TODO: 생성자 ID를 DB에 저장할 수 있도록 BirthdayVO에 필드 추가 및 처리 로직 필요

            BirthdayVO createdBoard = birthdayService.createBoard(newBoard, imageFile);

            response.put("success", true);
            response.put("message", "생일 보드가 성공적으로 생성되었습니다.");
            response.put("boardId", createdBoard.getId());
            return new ResponseEntity<>(response, HttpStatus.CREATED); // 201 Created
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "보드 생성 중 오류가 발생했습니다: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- 4. 게시글 수정 및 삭제 (Update/Delete) ---
    // 실제 구현 시, 수정 권한 확인 로직(로그인된 사용자 ID == 게시물 작성자 ID)이 Service 계층에서 반드시 필요합니다.

    /**
     * [PUT] 생일 보드 정보 수정
     * URL: /birthdays/{boardId}
     */
    @PutMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> updateBirthdayBoard(
            @PathVariable int boardId,
            @RequestBody BirthdayVO updatedVo,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        Integer loggedInUserId = (Integer) session.getAttribute("loggedInUser");
        if (loggedInUserId == null) {
            return unauthorizedResponse(response);
        }

        try {
            updatedVo.setId(boardId);
            // TODO: Service에서 권한 확인 로직을 추가해야 합니다.
            BirthdayVO result = birthdayService.updateBoard(updatedVo);

            response.put("success", true);
            response.put("message", "게시물이 성공적으로 수정되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); // 403 Forbidden (권한 문제 포함)
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "게시물 수정에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * [DELETE] 생일 보드 삭제(소프트 삭제)
     * URL: /birthdays/{boardId}
     */
    @DeleteMapping("/{boardId}")
    public ResponseEntity<Map<String, Object>> deleteBirthdayBoard(
            @PathVariable int boardId,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        Integer loggedInUserId = (Integer) session.getAttribute("loggedInUser");
        if (loggedInUserId == null) {
            return unauthorizedResponse(response);
        }

        try {
            // TODO: Service에서 권한 확인 로직을 추가해야 합니다.
            birthdayService.deleteBoard(boardId);
            response.put("success", true);
            response.put("message", "게시물이 성공적으로 삭제되었습니다.");
            return new ResponseEntity<>(response, HttpStatus.NO_CONTENT); // 204 No Content
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN); // 403 Forbidden (권한 문제 포함)
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "게시물 삭제에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- 5. 축하 메시지 (댓글) 관리 ---
    /**
     * [POST] 특정 보드에 축하 메시지 작성
     * URL: /birthdays/{boardId}/messages
     */
    @PostMapping("/{boardId}/messages")
    public ResponseEntity<Map<String, Object>> createMessage(
            @PathVariable int boardId,
            @RequestBody Map<String, String> request,
            HttpSession session) {

        Map<String, Object> response = new HashMap<>();
        Integer loggedInUserId = (Integer) session.getAttribute("loggedInUser");
        if (loggedInUserId == null) {
            return unauthorizedResponse(response);
        }

        try {
            String commentText = request.get("comment_text");
            if (commentText == null || commentText.trim().isEmpty()) {
                throw new IllegalArgumentException("메시지 내용을 입력해주세요.");
            }

            MessageVO message = birthdayService.createMessage(boardId, loggedInUserId, commentText);
            response.put("success", true);
            response.put("message", "메시지가 성공적으로 작성되었습니다.");
            // 댓글 작성자 닉네임을 응답에 포함하기 위해 닉네임 조회 로직 필요
            // message.setNickname(memberService.getMemberById(loggedInUserId).getNickname());
            response.put("data", message);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            response.put("success", false);
            response.put("message", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "메시지 작성에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * [GET] 보드별 축하 메시지 목록 조회
     * URL: /birthdays/{boardId}/messages
     */
    @GetMapping("/{boardId}/messages")
    public ResponseEntity<Map<String, Object>> getMessagesByBoardId(@PathVariable int boardId) {
        Map<String, Object> response = new HashMap<>();
        try {
            List<MessageVO> messages = birthdayService.getMessagesByBoardId(boardId);

            // TODO: MessageVO에는 userId만 있으므로, 닉네임을 포함하도록 DTO 변환 로직이 Service/Controller 단에서 필요합니다.

            response.put("success", true);
            response.put("messages", messages);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "메시지 목록 조회에 실패했습니다.");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // --- 6. 추가 기능 (오늘 생일자, 통계) ---
    /**
     * [GET] 오늘 생일자 조회
     * URL: /birthdays/today
     */
    @GetMapping("/today")
    public ResponseEntity<Map<String, Object>> getTodayBirthdays() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "미구현된 기능입니다. (Mapper/Service에 로직 추가 필요)");
        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED); // 501
    }

    /**
     * [GET] 서비스 통계 조회
     * URL: /statistics/summary
     */
    @GetMapping("/statistics/summary")
    public ResponseEntity<Map<String, Object>> getServiceStatistics() {
        Map<String, Object> response = new HashMap<>();
        response.put("success", false);
        response.put("message", "미구현된 기능입니다. (Service에 로직 추가 필요)");
        return new ResponseEntity<>(response, HttpStatus.NOT_IMPLEMENTED); // 501
    }

    // --- 유틸리티 메서드 ---
    private ResponseEntity<Map<String, Object>> unauthorizedResponse(Map<String, Object> response) {
        response.put("success", false);
        response.put("message", "인증되지 않은 사용자입니다. 로그인해주세요.");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED); // 401
    }
}