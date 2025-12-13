<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>회원가입</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
  <link rel="stylesheet" as="style" crossorigin href="https://cdn.jsdelivr.net/gh/orioncactus/pretendard@v1.3.8/dist/web/static/pretendard.css" />
</head>
<body>
<div class="container fade-in-up">
  <div class="login-card">
    <div class="header-nav">
      <a href="${pageContext.request.contextPath}/login" class="back-link">← 뒤로</a>
    </div>

    <h1 class="logo">회원가입</h1>
    <p class="subtitle">서비스 이용을 위해<br>정보를 입력해 주세요.</p>

    <form action="${pageContext.request.contextPath}/signupAction" method="post">
      <div class="input-group">
        <label for="name">이름</label>
        <input type="text" id="name" name="name" placeholder="홍길동" required>
      </div>

      <div class="input-group">
        <label for="id">아이디</label>
        <input type="text" id="id" name="id" placeholder="영문, 숫자 포함 6자 이상" required>
      </div>

      <div class="input-group">
        <label for="password">비밀번호</label>
        <input type="password" id="password" name="password" placeholder="비밀번호 입력" required>
      </div>

      <div class="input-group">
        <label for="passwordConfirm">비밀번호 확인</label>
        <input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="비밀번호 재입력" required>
      </div>

      <div class="input-group">
        <label>닉네임</label>
        <input type="text" name="nickname" placeholder="닉네임을 입력하세요" required>
      </div>

      <div class="input-group">
        <label>이메일</label>
        <input type="email" name="email" placeholder="example@email.com">
      </div>

      <button type="submit" class="btn-primary" style="margin-top: 20px;">
        동의하고 가입하기
      </button>
    </form>
  </div>
</div>
</body>
</html>