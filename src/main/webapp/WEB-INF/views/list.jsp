<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>홈 - 한동 온라인 생축게</title>
  <style>
    body { margin: 0; background-color: #F2F4F6; font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", sans-serif; }

    /* 1. 상단 네비게이션 바 */
    .header { background: white; padding: 15px 20px; display: flex; justify-content: space-between; align-items: center; position: sticky; top: 0; z-index: 100; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
    .logo { font-size: 20px; font-weight: 800; color: #0fc7ff; text-decoration: none; } /* 당근색 로고 */
    .user-menu { display: flex; gap: 15px; align-items: center; font-size: 15px; font-weight: 600; color: #10e3aa; }
    .welcome-msg { color: #212529; }
    .btn-small { padding: 8px 14px; font-size: 13px; border-radius: 6px; text-decoration: none; font-weight: 600; transition: 0.2s; }
    .btn-gray { background: #F2F4F6; color: #7a8be1; }
    .btn-gray:hover { background: #E1E4E8; }
    .btn-admin { background: #589ee6; color: white; }

    /* 2. 당근마켓 스타일 Hero Section (검색 영역) */
    .hero-section { background-color: #212124; color: white; padding: 80px 20px; text-align: center; }
    .hero-title { font-size: 32px; font-weight: 700; margin-bottom: 30px; line-height: 1.4; }
    .search-box { max-width: 500px; margin: 0 auto; position: relative; }
    .search-input { width: 100%; padding: 18px 25px; border-radius: 50px; border: none; font-size: 16px; box-sizing: border-box; outline: none; box-shadow: 0 4px 10px rgba(0,0,0,0.1); }
    .search-btn { position: absolute; right: 10px; top: 50%; transform: translateY(-50%); background: none; border: none; font-size: 20px; cursor: pointer; color: #868E96; }

    /* 3. 게시글 리스트 영역 */
    .container { max-width: 700px; margin: 40px auto; padding: 0 20px; padding-bottom: 80px; }
    .section-title { font-size: 20px; font-weight: 700; margin-bottom: 20px; color: #212529; }

    .card { background: white; border-radius: 16px; overflow: hidden; margin-bottom: 24px; box-shadow: 0 4px 12px rgba(0,0,0,0.04); transition: transform 0.2s; cursor: pointer; border: 1px solid #eee; display: flex; flex-direction: column; }
    .card:hover { transform: translateY(-3px); box-shadow: 0 8px 20px rgba(0,0,0,0.08); }

    .card-img { width: 100%; height: 300px; object-fit: cover; background-color: #f8f9fa; }
    .card-body { padding: 24px; }
    .badge { background: #FFF1EA; color: #0f97ff; padding: 6px 10px; border-radius: 6px; font-size: 13px; font-weight: 700; display: inline-block; margin-bottom: 12px; }
    .card-title { font-size: 20px; font-weight: 700; color: #212529; margin: 0 0 10px 0; }
    .card-meta { font-size: 14px; color: #868E96; display: flex; justify-content: space-between; margin-top: 15px; border-top: 1px solid #F1F3F5; padding-top: 15px; }

    /* 글쓰기 버튼 (FAB) */
    .fab { position: fixed; bottom: 30px; right: 30px; width: 60px; height: 60px; background-color: #0075d8; border-radius: 50%; box-shadow: 0 4px 12px rgba(28, 106, 204, 0.4); display: flex; justify-content: center; align-items: center; color: white; font-size: 30px; text-decoration: none; font-weight: 300; transition: 0.2s; z-index: 200; }
    .fab:hover { transform: scale(1.1); background-color: #0050e6; }

    /* 게시물 없을 때 */
    .empty-state { text-align: center; padding: 50px 0; color: #868E96; }
  </style>
</head>
<body>
<div class="header">
  <a href="${pageContext.request.contextPath}/board/list" class="logo">HGU Birthday Post🎉</a>
  <div class="user-menu">
    <span class="welcome-msg">${sessionScope.loginMember.nickname}님 반갑습니다!!</span>

    <c:if test="${sessionScope.loginMember.role == 'ADMIN'}">
      <a href="${pageContext.request.contextPath}/admin/main" class="btn-small btn-admin">관리</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/member/logout" class="btn-small btn-gray">로그아웃</a>
  </div>
</div>

<div class="hero-section">
  <div class="hero-title">
    오늘은 어떤 생일자의<br>
    게시물이 올라왔을까요? 🎉
  </div>
  <form action="${pageContext.request.contextPath}/board/list" method="get" class="search-box">
    <input type="text" name="keyword" class="search-input" placeholder="이름이나 공동체를 검색해보세요" value="${param.keyword}">
    <button type="submit" class="search-btn">🔍</button>
  </form>
</div>

<div class="container">
  <div class="section-title">
    <c:choose>
      <c:when test="${not empty param.keyword}">
        '${param.keyword}' 검색 결과
      </c:when>
      <c:otherwise>
        최근 올라온 축하글
      </c:otherwise>
    </c:choose>
  </div>

  <c:if test="${empty posts}">
    <div class="empty-state">
      <p>아직 등록된 게시물이 없어요. 🥲<br>첫 번째 축하글의 주인공이 되어보세요!</p>
    </div>
  </c:if>

  <c:forEach var="post" items="${posts}">
    <div class="card" onclick="location.href='${pageContext.request.contextPath}/board/view/${post.id}'">
      <c:if test="${not empty post.birthdayImgUrl}">
        <img src="${pageContext.request.contextPath}/resources/upload/${post.birthdayImgUrl}" class="card-img">
      </c:if>
      <div class="card-body">
        <span class="badge">${post.groupName}</span>
        <h3 class="card-title">${post.birthdayPersonName}님의 생일이에요!</h3>
        <div class="card-meta">
          <span>🎂 <fmt:formatDate value="${post.birthdayDate}" pattern="yyyy-MM-dd"/></span>
          <span>조회 ${post.viewCount}</span>
        </div>
      </div>
    </div>
  </c:forEach>
</div>

<a href="${pageContext.request.contextPath}/board/write" class="fab">+</a>
</body>
</html>