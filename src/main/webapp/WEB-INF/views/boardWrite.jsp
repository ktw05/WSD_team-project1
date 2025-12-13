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

    <h1 class="logo" style="font-size: 22px;">축하 메시지 보내기</h1>

    <form action="${pageContext.request.contextPath}/board/writeAction" method="post">

      <div class="input-group">
        <label for="targetName">누구에게 보내나요?</label>
        <input type="text" id="targetName" name="title" placeholder="받는 친구 이름 (예: 예찬)" required>
      </div>

      <div class="input-group">
        <label for="content">내용</label>
        <textarea id="content" name="content" class="text-area-custom" placeholder="따뜻한 축하의 말을 남겨주세요." required></textarea>
      </div>

      <button type="submit" class="btn-primary">
        전송하기
      </button>
    </form>
  </div>
</div>
</body>
</html>