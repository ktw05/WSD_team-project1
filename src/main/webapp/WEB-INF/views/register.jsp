<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>회원가입</title>
  <style>
    body { margin: 0; padding: 0; font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", "Malgun Gothic", sans-serif; background-color: #F2F4F6; display: flex; justify-content: center; align-items: center; min-height: 100vh; }
    .container { width: 100%; max-width: 400px; padding: 20px; }
    .card { background: white; padding: 30px; border-radius: 24px; box-shadow: 0 10px 40px rgba(0,0,0,0.05); }
    h2 { text-align: center; margin-bottom: 30px; color: #191F28; }
    .form-group { margin-bottom: 15px; }
    label { display: block; margin-bottom: 8px; font-size: 14px; color: #4E5968; font-weight: 600; }
    input { width: 100%; padding: 14px; border: none; background-color: #F2F4F6; border-radius: 12px; font-size: 15px; box-sizing: border-box; }
    input:focus { outline: none; background-color: #E8F3FF; color: #191F28; }
    button { width: 100%; padding: 16px; background-color: #3182F6; color: white; border: none; border-radius: 12px; font-size: 16px; font-weight: 700; cursor: pointer; margin-top: 10px; }
    .back-link { display: block; text-align: center; margin-top: 20px; color: #8B95A1; text-decoration: none; font-size: 14px; }
  </style>
</head>
<body>
<div class="container">
  <div class="card">
    <h2>회원가입</h2>
    <form action="${pageContext.request.contextPath}/member/registerAction" method="post">
      <div class="form-group">
        <label>아이디</label>
        <input type="text" name="username" placeholder="로그인에 쓸 아이디" required>
      </div>
      <div class="form-group">
        <label>비밀번호</label>
        <input type="password" name="password" placeholder="비밀번호" required>
      </div>
      <div class="form-group">
        <label>닉네임</label>
        <input type="text" name="nickname" placeholder="화면에 보일 이름" required>
      </div>
      <div class="form-group">
        <label>이메일</label>
        <input type="email" name="email" placeholder="example@handong.edu" required>
      </div>
      <button type="submit">가입하기</button>
    </form>
    <a href="${pageContext.request.contextPath}/member/login" class="back-link">로그인으로 돌아가기</a>
  </div>
</div>
</body>
</html>