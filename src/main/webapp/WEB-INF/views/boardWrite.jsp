<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>축하 글쓰기</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard.css" />
</head>
<body>
<div class="container fade-in-up">
  <div class="login-card">

    <div class="header-nav">
      <a href="${pageContext.request.contextPath}/board/list" class="back-link">← 취소</a>
    </div>

    <h1 class="logo" style="font-size: 22px;">축하 보드 만들기 🎂</h1>

    <form action="${pageContext.request.contextPath}/board/writeAction" method="post">

      <!-- 작성 공동체 -->
      <div class="input-group">
        <label for="groupName">작성 공동체</label>
        <input type="text" id="groupName" name="groupName" placeholder="예: 한동대 24학번" required>
      </div>

      <!-- 생일자 이름 -->
      <div class="input-group">
        <label for="birthdayPersonName">누구의 생일인가요?</label>
        <input type="text" id="birthdayPersonName" name="birthdayPersonName" placeholder="예: 예찬" required>
      </div>

      <!-- 생일 날짜 -->
      <div class="input-group">
        <label for="birthdayDate">생일</label>
        <input type="date" id="birthdayDate" name="birthdayDate" required>
      </div>

      <!-- 이미지 URL (선택) -->
      <div class="input-group">
        <label for="birthdayImgUrl">대표 이미지 URL (선택)</label>
        <input type="text" id="birthdayImgUrl" name="birthdayImgUrl" placeholder="https://... (없으면 비워도 됨)">
      </div>

      <!-- 축하 문구 -->
      <div class="input-group">
        <label for="celebrationText">축하 메시지</label>
        <textarea id="celebrationText" name="celebrationText" class="text-area-custom"
                  placeholder="따뜻한 축하의 말을 남겨주세요." required></textarea>
      </div>

      <button type="submit" class="btn-primary">
        전송하기
      </button>
    </form>
  </div>
</div>
</body>
</html>
