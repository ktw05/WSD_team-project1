// service/BirthdayServiceImpl.java

package org.example.wsdfinalteamproject.service;

import org.example.wsdfinalteamproject.domain.BirthdayVO;
import org.example.wsdfinalteamproject.domain.MessageVO;
import org.example.wsdfinalteamproject.mapper.BirthdayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class BirthdayServiceImpl implements BirthdayService {

    private final BirthdayMapper birthdayMapper;
    // 실제 경로 설정 (application.properties 등에서 주입받는 것이 좋음)
    private static final String UPLOAD_DIR = "/path/to/upload/images/";

    @Autowired
    public BirthdayServiceImpl(BirthdayMapper birthdayMapper) {
        this.birthdayMapper = birthdayMapper;
        // 디렉토리가 없으면 생성 (개발 환경 설정)
        new File(UPLOAD_DIR).mkdirs();
    }

    @Override
    @Transactional
    public BirthdayVO createBoard(BirthdayVO birthdayVo, MultipartFile imageFile) {
        // 1. 파일 업로드 처리
        if (imageFile != null && !imageFile.isEmpty()) {
            String originalFilename = imageFile.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            String savePath = UPLOAD_DIR + uniqueFileName;

            try {
                // 파일을 지정된 경로에 저장
                imageFile.transferTo(new File(savePath));
                // DB에는 접근 가능한 URL 또는 파일 경로 저장 (여기서는 파일 경로 저장 가정)
                birthdayVo.setBirthdayImgUrl("/images/" + uniqueFileName);
            } catch (IOException e) {
                // 파일 저장 실패 시 예외 처리
                throw new RuntimeException("이미지 파일 업로드 실패", e);
            }
        }

        // 2. DB 저장
        int result = birthdayMapper.insertBirthdayBoard(birthdayVo);
        if (result == 0) {
            throw new RuntimeException("생일 보드 DB 저장 실패");
        }

        // 자동 생성된 ID가 포함된 VO 반환
        return birthdayVo;
    }

    @Override
    public List<BirthdayVO> getBoardList(String searchKeyword, String sortCriteria) {
        // 검색어, 정렬 기준 유효성 검사 및 기본값 설정 로직 추가 가능
        return birthdayMapper.selectBirthdayList(searchKeyword, sortCriteria);
    }

    @Override
    @Transactional
    public BirthdayVO getBoardDetail(int boardId) {
        // 1. 조회수 증가
        birthdayMapper.incrementViewCount(boardId);

        // 2. 상세 정보 조회
        BirthdayVO board = birthdayMapper.selectBirthdayBoardById(boardId);

        if (board == null) {
            throw new IllegalArgumentException("존재하지 않는 게시물 ID입니다.");
        }
        return board;
    }

    @Override
    @Transactional
    public BirthdayVO updateBoard(BirthdayVO birthdayVo) {
        // 수정 권한 확인 로직 (Controller 또는 Service 단에서 현재 로그인 사용자 ID와 비교)

        int result = birthdayMapper.updateBirthdayBoard(birthdayVo);
        if (result == 0) {
            throw new IllegalArgumentException("게시물 수정 실패 또는 권한 없음.");
        }
        return birthdayVo;
    }

    @Override
    @Transactional
    public void deleteBoard(int boardId) {
        // 삭제 권한 확인 로직 필요

        // 실제로는 소프트 삭제(deleted_at 업데이트)를 권장합니다.
        int result = birthdayMapper.deleteBirthdayBoard(boardId);
        if (result == 0) {
            throw new IllegalArgumentException("게시물 삭제 실패 또는 권한 없음.");
        }
    }

    @Override
    @Transactional
    public MessageVO createMessage(int boardId, int userId, String commentText) {
        MessageVO messageVo = new MessageVO();
        messageVo.setPostId(boardId);
        messageVo.setUserId(userId);
        messageVo.setCommentText(commentText);

        int result = birthdayMapper.insertMessage(messageVo);
        if (result == 0) {
            throw new RuntimeException("메시지 작성 DB 저장 실패");
        }
        return messageVo;
    }

    @Override
    public List<MessageVO> getMessagesByBoardId(int boardId) {
        return birthdayMapper.selectMessagesByBoardId(boardId);
    }
}