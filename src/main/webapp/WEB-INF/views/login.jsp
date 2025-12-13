<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>로그인</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard.css" />
</head>
<body>
<div class="container fade-in-up">
  <div class="login-card">
    <h1 class="logo">한동 온라인 생축게</h1>
    <p class="subtitle">에 오신걸 환영합니다!</p>

    <form action="${pageContext.request.contextPath}/loginAction" method="post">
      <div class="input-group">
        <label for="id">아이디</label>
        <input type="text" id="id" name="id" placeholder="아이디를 입력해주세요" required>
      </div>

      <div class="input-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" placeholder="비밀번호를 입력해주세요" required>
      </div>

      <button type="submit" class="btn-primary">로그인</button>
    </form>

    <div class="links">
      <a href="${pageContext.request.contextPath}/signup">회원가입</a>
    </div>
  </div>
</div>
</body>
</html>