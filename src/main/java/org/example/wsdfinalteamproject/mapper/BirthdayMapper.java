// mapper/BirthdayMapper.java

package org.example.wsdfinalteamproject.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.wsdfinalteamproject.domain.BirthdayVO;
import org.example.wsdfinalteamproject.domain.MessageVO;

import java.util.List;

@Mapper
public interface BirthdayMapper {

    // --- Birthday Board 관련 ---

    /**
     * 새로운 생일 보드를 DB에 삽입합니다. (Create)
     */
    int insertBirthdayBoard(BirthdayVO birthdayVo);

    /**
     * 생일 보드 목록을 조회합니다. (Read - List)
     * @param searchKeyword 검색 키워드
     * @param sortCriteria 정렬 기준 (latest, popular, birthday)
     * @return BirthdayVO 리스트
     */
    List<BirthdayVO> selectBirthdayList(
            @Param("searchKeyword") String searchKeyword,
            @Param("sortCriteria") String sortCriteria);

    /**
     * 특정 생일 보드를 ID로 조회합니다. (Read - Detail)
     */
    BirthdayVO selectBirthdayBoardById(@Param("id") int id);

    /**
     * 생일 보드의 정보를 수정합니다. (Update)
     */
    int updateBirthdayBoard(BirthdayVO birthdayVo);

    /**
     * 생일 보드를 삭제합니다. (Delete - 소프트 삭제의 경우, deleted_at 필드 업데이트 로직이 필요)
     */
    int deleteBirthdayBoard(@Param("id") int id);

    /**
     * 게시글 조회수를 1 증가시킵니다. (추가 기능)
     */
    int incrementViewCount(@Param("id") int id);

    // --- Message (댓글) 관련 ---

    /**
     * 특정 보드에 축하 메시지 (댓글)를 삽입합니다.
     */
    int insertMessage(MessageVO messageVo);

    /**
     * 특정 보드의 축하 메시지 목록을 조회합니다.
     */
    List<MessageVO> selectMessagesByBoardId(@Param("postId") int postId);

}