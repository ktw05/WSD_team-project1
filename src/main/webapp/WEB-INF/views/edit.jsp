<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>글 수정</title>
  <style>
    body { margin: 0; background-color: #F2F4F6; font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", sans-serif; }
    .header { background: white; padding: 15px 20px; display: flex; align-items: center; position: sticky; top: 0; }
    .back-btn { font-size: 24px; text-decoration: none; color: #191F28; margin-right: 15px; }
    .page-title { font-size: 18px; font-weight: 700; }

    .container { max-width: 600px; margin: 20px auto; padding: 0 20px; }
    .card { background: white; border-radius: 20px; padding: 30px; box-shadow: 0 4px 20px rgba(0,0,0,0.05); }

    label { display: block; margin-bottom: 8px; font-size: 14px; font-weight: 600; color: #4E5968; margin-top: 20px; }
    label:first-child { margin-top: 0; }

    input[type="text"], input[type="date"], textarea { width: 100%; padding: 15px; border: none; background-color: #F2F4F6; border-radius: 12px; font-size: 15px; box-sizing: border-box; font-family: inherit; }
    textarea { height: 150px; resize: none; }
    input:focus, textarea:focus { outline: none; background-color: #E8F3FF; }

    input[type="file"] { margin-top: 5px; font-size: 14px; }

    .submit-btn { width: 100%; padding: 16px; background-color: #3182F6; color: white; border: none; border-radius: 16px; font-size: 16px; font-weight: 700; margin-top: 30px; cursor: pointer; }
    .hint { font-size: 13px; color: #8B95A1; margin-top: 8px; }
    .preview-img { width: 100%; margin-top: 10px; border-radius: 12px; background: #000; object-fit: contain; max-height: 320px; }
  </style>
</head>
<body>
<div class="header">
  <a href="${pageContext.request.contextPath}/board/view/${post.id}" class="back-btn">←</a>
  <span class="page-title">글 수정</span>
</div>

<div class="container">
  <form action="${pageContext.request.contextPath}/board/editAction" method="post" enctype="multipart/form-data">
    <div class="card">
      <input type="hidden" name="id" value="${post.id}">

      <label>새새/공동체 이름</label>
      <input type="text" name="groupName" value="${post.groupName}" required>

      <label>생일 주인공 이름</label>
      <input type="text" name="birthdayPersonName" value="${post.birthdayPersonName}" required>

      <label>생일 날짜</label>
      <input type="date" name="birthdayDate"
             value="<fmt:formatDate value='${post.birthdayDate}' pattern='yyyy-MM-dd'/>" required>

      <label>삼행시/축하글</label>
      <textarea name="celebrationText" required>${post.celebrationText}</textarea>

      <label>사진 업로드(선택)</label>
      <input type="file" name="file">
      <div class="hint">새 사진을 선택하지 않으면 기존 사진이 유지돼요.</div>

      <c:if test="${not empty post.birthdayImgUrl}">
        <img class="preview-img" src="${pageContext.request.contextPath}/resources/upload/${post.birthdayImgUrl}">
      </c:if>

      <button type="submit" class="submit-btn">수정 완료</button>
    </div>
  </form>
</div>
</body>
</html>
