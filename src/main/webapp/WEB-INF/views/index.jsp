<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>메인 화면</title>
  <style>
    /* 기본 스타일 초기화 */
    * {
      box-sizing: border-box;
      margin: 0;
      padding: 0;
    }

    body {
      font-family: 'Apple SD Gothic Neo', 'Malgun Gothic', sans-serif;
      background-color: #f5f7fa; /* 아주 연한 회색 배경 */
      color: #333;
      line-height: 1.6;
    }

    /* 1. 메인 컨테이너 스타일 (화면 넓히기) */
    .container {
      max-width: 800px; /* 기존 좁은 폭 -> 800px로 확대 */
      width: 90%;       /* 모바일에서는 좌우 여백 5%씩 */
      margin: 60px auto; /* 위아래 여백 */
      background-color: white;
      padding: 40px;
      border-radius: 15px; /* 둥근 모서리 */
      box-shadow: 0 4px 12px rgba(0,0,0,0.1); /* 부드러운 그림자 */
    }

    /* 2. 환영 메시지 스타일 */
    .welcome-header {
      display: flex;
      justify-content: space-between; /* 텍스트와 로그아웃 버튼 양옆 배치 */
      align-items: flex-start;
      margin-bottom: 30px;
    }

    .welcome-text {
      font-size: 26px;
      font-weight: 700;
      color: #2c3e50;
    }

    .sub-text {
      font-size: 16px;
      color: #7f8c8d;
      font-weight: normal;
      margin-top: 5px;
    }

    .logout-btn {
      font-size: 14px;
      color: #e74c3c; /* 빨간색 */
      text-decoration: none;
      border: 1px solid #e74c3c;
      padding: 5px 12px;
      border-radius: 20px;
      transition: all 0.3s;
    }

    .logout-btn:hover {
      background-color: #e74c3c;
      color: white;
    }

    /* 구분선 */
    hr {
      border: 0;
      height: 1px;
      background: #e0e0e0;
      margin: 30px 0;
    }

    /* 3. 바로가기 메뉴 리스트 */
    .menu-section h3 {
      font-size: 18px;
      margin-bottom: 15px;
      color: #34495e;
    }

    .menu-list {
      display: flex;
      flex-direction: column;
      gap: 15px; /* 메뉴 사이 간격 */
    }

    .menu-item {
      display: block; /* a 태그를 블록으로 */
      text-decoration: none;
      background-color: #fff;
      border: 1px solid #eee;
      padding: 20px;
      border-radius: 10px;
      transition: transform 0.2s, box-shadow 0.2s;
      color: #333;
    }

    .menu-item:hover {
      transform: translateY(-3px); /* 살짝 떠오르는 효과 */
      box-shadow: 0 5px 15px rgba(0,0,0,0.05);
      border-color: #ddd;
    }

    .menu-title {
      font-size: 18px;
      font-weight: bold;
      display: block;
      margin-bottom: 5px;
    }

    .menu-desc {
      font-size: 14px;
      color: #888;
    }

    .icon {
      margin-right: 8px;
      font-size: 20px;
    }

  </style>
</head>
<body>

<div class="container">
  <div class="welcome-header">
    <div class="welcome-text">
      <c:out value="${nickname}" default="${userName}" /> 님 반가워요,<br>
      <div class="sub-text">오늘도 축하할 일이 있나요? 🎉</div>
    </div>
    <a href="${pageContext.request.contextPath}/login" class="logout-btn">로그아웃</a>
  </div>

  <hr>

  <div class="menu-section">
    <h3>바로가기</h3>

    <div class="menu-list">
      <a href="${pageContext.request.contextPath}/board/list" class="menu-item">
        <span class="menu-title"><span class="icon">🎂</span> 축하 게시판 구경하기</span>
        <span class="menu-desc">지금까지 128개의 축하가 오갔어요</span>
      </a>

      <a href="#" class="menu-item">
        <span class="menu-title"><span class="icon">📅</span> 내 생일 / 받은 편지함</span>
        <span class="menu-desc">친구들이 보낸 메시지 확인하기</span>
      </a>
    </div>
  </div>
</div>

</body>
</html>