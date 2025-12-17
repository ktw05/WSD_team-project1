<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>로그인 - 한동 온라인 생축게</title>
  <style>
    body { margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Malgun Gothic", sans-serif; background-color: #F2F4F6; display: flex; justify-content: center; align-items: center; height: 100vh; }
    .container { width: 100%; max-width: 400px; padding: 20px; }
    .card { background: white; padding: 40px 30px; border-radius: 24px; box-shadow: 0 10px 40px rgba(0,0,0,0.05); text-align: center; }
    h2 { margin: 0 0 10px 0; font-size: 24px; color: #191F28; }
    p { margin: 0 0 30px 0; color: #8B95A1; font-size: 15px; }
    input { width: 100%; padding: 15px; margin-bottom: 12px; border: 1px solid white; background-color: #F2F4F6; border-radius: 12px; font-size: 16px; box-sizing: border-box; transition: 0.2s; }
    input:focus { outline: none; background-color: #E8F3FF; border: 1px solid #3182F6; }
    button { width: 100%; padding: 16px; background-color: #3182F6; color: white; border: none; border-radius: 12px; font-size: 17px; font-weight: 700; cursor: pointer; margin-top: 10px; transition: 0.2s; }
    button:hover { background-color: #1B64DA; }
    .links { margin-top: 20px; font-size: 14px; }
    .links a { color: #8B95A1; text-decoration: none; margin: 0 10px; }
    .error { color: #FF3B30; font-size: 14px; margin-bottom: 15px; }
  </style>
</head>
<body>
<div class="container">
  <div class="card">
    <h2>반가워요 👋</h2>
    <p>한동 온라인 생일축하게시판 서비스입니다</p>

    <c:if test="${not empty error}">
      <div class="error">${error}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/member/loginAction" method="post">
      <input type="text" name="username" placeholder="아이디" required>
      <input type="password" name="password" placeholder="비밀번호" required>
      <button type="submit">로그인</button>
    </form>

    <div class="links">
      <a href="${pageContext.request.contextPath}/member/register">회원가입</a>
    </div>
  </div>
</div>
</body>
</html>