// service/BirthdayService.java

package org.example.wsdfinalteamproject.service;

import org.example.wsdfinalteamproject.domain.BirthdayVO;
import org.example.wsdfinalteamproject.domain.MessageVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface BirthdayService {

    /**
     * 생일 보드를 생성합니다. (이미지 파일 업로드 및 DB 저장 처리)
     * @param birthdayVo 게시물 정보
     * @param imageFile 축하 이미지 파일
     * @return 생성된 BirthdayVO
     */
    BirthdayVO createBoard(BirthdayVO birthdayVo, MultipartFile imageFile);

    /**
     * 검색 및 정렬 조건에 따라 생일 보드 목록을 조회합니다.
     */
    List<BirthdayVO> getBoardList(String searchKeyword, String sortCriteria);

    /**
     * 특정 생일 보드 상세 정보를 조회하고, 조회수를 증가시킵니다.
     * @param boardId 게시물 ID
     * @return BirthdayVO
     */
    BirthdayVO getBoardDetail(int boardId);

    /**
     * 생일 보드 정보를 수정합니다.
     */
    BirthdayVO updateBoard(BirthdayVO birthdayVo);

    /**
     * 생일 보드를 삭제합니다.
     */
    void deleteBoard(int boardId);

    /**
     * 특정 보드에 축하 메시지를 작성합니다.
     */
    MessageVO createMessage(int boardId, int userId, String commentText);

    /**
     * 특정 보드의 축하 메시지 목록을 조회합니다.
     */
    List<MessageVO> getMessagesByBoardId(int boardId);
}