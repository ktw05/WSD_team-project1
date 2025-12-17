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
    .header { background: white; padding: 15px 20px; display: flex; justify-content: space-between; align-items: center; position: sticky; top: 0; z-index: 100; box-shadow: 0 2px 10px rgba(0,0,0,0.03); }
    .logo { font-size: 20px; font-weight: 800; color: #191F28; text-decoration: none; }
    .user-menu { display: flex; gap: 10px; align-items: center; }
    .btn-small { padding: 8px 14px; font-size: 13px; border-radius: 8px; text-decoration: none; font-weight: 600; }
    .btn-gray { background: #F2F4F6; color: #4E5968; }
    .btn-admin { background: #333; color: white; } /* 관리자 버튼 색상 */

    .container { max-width: 600px; margin: 20px auto; padding: 0 20px; padding-bottom: 80px; }
    .card { background: white; border-radius: 20px; overflow: hidden; margin-bottom: 20px; box-shadow: 0 4px 20px rgba(0,0,0,0.05); transition: transform 0.2s; cursor: pointer; }
    .card:hover { transform: translateY(-3px); }
    .card-img { width: 100%; height: 250px; object-fit: cover; background-color: #eee; }
    .card-body { padding: 20px; }
    .badge { background: #E8F3FF; color: #3182F6; padding: 4px 8px; border-radius: 6px; font-size: 12px; font-weight: 700; display: inline-block; margin-bottom: 8px; }
    .card-title { font-size: 18px; font-weight: 700; color: #191F28; margin: 0 0 8px 0; }
    .card-meta { font-size: 13px; color: #8B95A1; display: flex; justify-content: space-between; }

    .fab { position: fixed; bottom: 30px; right: 30px; width: 60px; height: 60px; background-color: #3182F6; border-radius: 50%; box-shadow: 0 8px 20px rgba(49, 130, 246, 0.4); display: flex; justify-content: center; align-items: center; color: white; font-size: 30px; text-decoration: none; font-weight: 300; transition: 0.2s; }
    .fab:hover { transform: scale(1.1); background-color: #1B64DA; }

    a { text-decoration: none; color: inherit; }
  </style>
</head>
<body>
<div class="header">
  <a href="${pageContext.request.contextPath}/board/list" class="logo">HGU Birthday 🎉</a>
  <div class="user-menu">
    <c:if test="${sessionScope.loginMember.role == 'ADMIN'}">
      <a href="${pageContext.request.contextPath}/admin/main" class="btn-small btn-admin">관리</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/member/logout" class="btn-small btn-gray">로그아웃</a>
  </div>
</div>

<div class="container">
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