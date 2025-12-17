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
    .logo { font-size: 20px; font-weight: 800; color: #0fc7ff; text-decoration: none; }
    .user-menu { display: flex; gap: 15px; align-items: center; font-size: 15px; font-weight: 600; color: #10e3aa; }
    .welcome-msg { color: #212529; }
    .btn-small { padding: 8px 14px; font-size: 13px; border-radius: 6px; text-decoration: none; font-weight: 600; transition: 0.2s; }
    .btn-gray { background: #F2F4F6; color: #7a8be1; }
    .btn-gray:hover { background: #E1E4E8; }
    .btn-admin { background: #589ee6; color: white; }

    /* 2. Hero Section (검색 영역) */
    .hero-section { background-color: #212124; color: white; padding: 70px 20px; text-align: center; }
    .hero-title { font-size: 32px; font-weight: 700; margin-bottom: 26px; line-height: 1.4; }
    .search-box { max-width: 520px; margin: 0 auto; position: relative; }
    .search-input { width: 100%; padding: 18px 25px; border-radius: 50px; border: none; font-size: 16px; box-sizing: border-box; outline: none; box-shadow: 0 4px 10px rgba(0,0,0,0.12); }
    .search-btn { position: absolute; right: 10px; top: 50%; transform: translateY(-50%); background: none; border: none; font-size: 20px; cursor: pointer; color: #868E96; }

    /* 3. 컨테이너 */
    .container { max-width: 1040px; margin: 36px auto; padding: 0 20px; padding-bottom: 90px; }
    .section-title { font-size: 20px; font-weight: 700; margin-bottom: 18px; color: #212529; display:flex; align-items:center; justify-content:space-between; gap: 10px; }
    .count-chip { background:#fff; border:1px solid #e9ecef; color:#495057; padding:6px 10px; border-radius:999px; font-size: 13px; }

    /*  앨범(갤러리) 그리드 */
    .grid {
      display: grid;
      gap: 18px;
      grid-template-columns: repeat(3, minmax(0, 1fr));
    }

    /* 반응형 */
    @media (max-width: 960px) {
      .grid { grid-template-columns: repeat(2, minmax(0, 1fr)); }
      .container { max-width: 780px; }
    }
    @media (max-width: 560px) {
      .grid { grid-template-columns: 1fr; }
      .hero-title { font-size: 26px; }
    }

    /* ✅ 앨범 카드 */
    .album-card {
      background: white;
      border-radius: 16px;
      overflow: hidden;
      box-shadow: 0 4px 12px rgba(0,0,0,0.04);
      border: 1px solid #eee;
      cursor: pointer;
      transition: transform 0.18s, box-shadow 0.18s;
      display: flex;
      flex-direction: column;
      min-height: 360px;
    }
    .album-card:hover { transform: translateY(-3px); box-shadow: 0 10px 24px rgba(0,0,0,0.10); }

    /* 썸네일 영역(고정 비율) */
    .thumb {
      width: 100%;
      aspect-ratio: 4 / 3;   /* 앨범 느낌 핵심 */
      background: #f1f3f5;
      display: flex;
      align-items: center;
      justify-content: center;
      position: relative;
      overflow: hidden;
    }
    .thumb img {
      width: 100%;
      height: 100%;
      object-fit: cover;
      display: block;
    }
    /* 이미지 없을 때 */
    .thumb-placeholder {
      font-size: 42px;
      opacity: 0.9;
      user-select: none;
    }

    /* 카드 바디 */
    .album-body { padding: 18px 18px 16px 18px; display:flex; flex-direction:column; gap: 10px; flex: 1; }
    .badge { background: #FFF1EA; color: #0f97ff; padding: 6px 10px; border-radius: 8px; font-size: 13px; font-weight: 700; display: inline-block; width: fit-content; }
    .album-title { font-size: 18px; font-weight: 800; color: #212529; margin: 0; line-height: 1.25; }
    .album-desc { color:#495057; font-size: 14px; line-height: 1.45; display: -webkit-box; -webkit-line-clamp: 2; -webkit-box-orient: vertical; overflow: hidden; min-height: 40px; }
    .album-acrostic{
      white-space: pre-line;
      line-height: 1.55;
      display: -webkit-box;
      -webkit-line-clamp: 3;
      -webkit-box-orient: vertical;
      overflow: hidden;
    }



    .album-meta {
      margin-top: auto;
      padding-top: 12px;
      border-top: 1px solid #F1F3F5;
      font-size: 13px;
      color: #868E96;
      display:flex;
      justify-content: space-between;
      align-items: center;
      gap: 8px;
      flex-wrap: wrap;
    }
    .meta-left { display:flex; gap: 10px; align-items:center; }
    .meta-pill { background:#f8f9fa; border:1px solid #e9ecef; padding: 6px 10px; border-radius: 999px; color:#495057; }

    /* 글쓰기 버튼 (FAB) */
    .fab { position: fixed; bottom: 30px; right: 30px; width: 60px; height: 60px; background-color: #0075d8; border-radius: 50%; box-shadow: 0 4px 12px rgba(28, 106, 204, 0.4); display: flex; justify-content: center; align-items: center; color: white; font-size: 30px; text-decoration: none; font-weight: 300; transition: 0.2s; z-index: 200; }
    .fab:hover { transform: scale(1.08); background-color: #0050e6; }

    /* 게시물 없을 때 */
    .empty-state { text-align: center; padding: 50px 0; color: #868E96; background: white; border: 1px dashed #dee2e6; border-radius: 16px; }
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
    <span>
      <c:choose>
        <c:when test="${not empty param.keyword}">
          '${param.keyword}' 검색 결과
        </c:when>
        <c:otherwise>
          최근 올라온 축하글
        </c:otherwise>
      </c:choose>
    </span>
  </div>

  <c:if test="${empty posts}">
    <div class="empty-state">
      <p>아직 등록된 게시물이 없어요. 🥲<br>첫 번째 축하글의 주인공이 되어보세요!</p>
    </div>
  </c:if>

  <c:if test="${not empty posts}">
    <div class="grid">
      <c:forEach var="post" items="${posts}">
        <div class="album-card" onclick="location.href='${pageContext.request.contextPath}/board/view/${post.id}'">
          <div class="thumb">
            <c:choose>
              <c:when test="${not empty post.birthdayImgUrl}">
                <img src="${pageContext.request.contextPath}/resources/upload/${post.birthdayImgUrl}" alt="birthday image">
              </c:when>
              <c:otherwise>
                <div class="thumb-placeholder">🎂</div>
              </c:otherwise>
            </c:choose>
          </div>

          <div class="album-body">
            <span class="badge">From. ${post.groupName}</span>
            <h3 class="album-title">${post.birthdayPersonName}님의 생일이에요!</h3>

            <div class="album-desc album-acrostic">
              <c:out value="${post.celebrationText}"/>
            </div>



            <div class="album-meta">
              <div class="meta-left">
                <span class="meta-pill">🎂 <fmt:formatDate value="${post.birthdayDate}" pattern="yyyy-MM-dd"/></span>
                <span class="meta-pill">조회 ${post.viewCount}</span>
              </div>

              <div>
                <span>🕒 <fmt:formatDate value="${post.createdAt}" pattern="MM/dd HH:mm"/></span>

                <c:if test="
      ${sessionScope.loginMember.role == 'ADMIN'
        || sessionScope.loginMember.userId == post.userId}
    ">
                  ·
                  <a
                          href="${pageContext.request.contextPath}/board/delete/${post.id}"
                          onclick="event.stopPropagation(); return confirm('정말 삭제하시겠습니까?');"
                          style="color:#ff4d4f; font-weight:700; font-size:12px; text-decoration:none;"
                  >
                    삭제
                  </a>
                </c:if>
              </div>
            </div>

          </div>
        </div>
      </c:forEach>
    </div>
  </c:if>
</div>

<a href="${pageContext.request.contextPath}/board/write" class="fab">+</a>

</body>
</html>
