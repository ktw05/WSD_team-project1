<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>축하 메시지</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard.css" />
</head>
<body>
<div class="container fade-in-up">

  <div class="page-header">
    <a href="${pageContext.request.contextPath}/main" class="back-link">← 홈으로</a>
    <h2>마음을 담은 메시지 💌</h2>
  </div>

  <div class="feed-list">

    <div class="feed-card">
      <div class="feed-header">
        <span class="writer-name">김토스</span>
        <span class="write-date">방금 전</span>
      </div>
      <div class="feed-content">
        예찬아 생일 진짜 축하해! 이번 프로젝트 끝나고 맛있는거 먹으러 가자 🎉
      </div>
    </div>

    <div class="feed-card">
      <div class="feed-header">
        <span class="writer-name">이한동</span>
        <span class="write-date">10분 전</span>
      </div>
      <div class="feed-content">
        Happy Birthday! 항상 응원하고 있어.
      </div>
    </div>

  </div>

  <a href="${pageContext.request.contextPath}/board/write" class="fab-btn">
    +
  </a>

</div>
</body>
</html>