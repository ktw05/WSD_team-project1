<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>${post.birthdayPersonName}님의 생일</title>
  <style>
    body { margin: 0; background-color: #F2F4F6; font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", sans-serif; padding-bottom: 80px; }
    .header { background: white; padding: 15px 20px; position: sticky; top: 0; z-index: 100; border-bottom: 1px solid #eee; display: flex; justify-content: space-between; align-items: center; }
    .back-btn { text-decoration: none; font-size: 20px; color: #191F28; }
    .del-btn { color: #FF3B30; text-decoration: none; font-size: 14px; font-weight: 600; }

    .container { max-width: 600px; margin: 0 auto; background: white; min-height: 100vh; }
    .post-img { width: 100%; max-height: 400px; object-fit: contain; background-color: #000; }
    .content { padding: 25px; }
    .badge { background: #E8F3FF; color: #3182F6; padding: 4px 8px; border-radius: 6px; font-size: 13px; font-weight: 700; margin-bottom: 10px; display: inline-block; }
    .title { font-size: 24px; font-weight: 800; color: #191F28; margin: 0 0 10px 0; }
    .date { color: #8B95A1; font-size: 14px; margin-bottom: 20px; }
    .text { font-size: 16px; line-height: 1.6; color: #333; white-space: pre-wrap; }

    .divider { height: 10px; background-color: #F2F4F6; border-top: 1px solid #eee; border-bottom: 1px solid #eee; }

    .comments-area { padding: 20px; }
    .comment-title { font-size: 16px; font-weight: 700; margin-bottom: 15px; }
    .comment-item { display: flex; justify-content: space-between; margin-bottom: 15px; border-bottom: 1px solid #f5f5f5; padding-bottom: 10px; }
    .cmt-user { font-weight: 600; font-size: 14px; margin-right: 8px; color: #191F28; }
    .cmt-text { font-size: 14px; color: #4E5968; }
    .cmt-del { color: #8B95A1; font-size: 12px; text-decoration: none; margin-left: 10px; }

    .comment-form { position: fixed; bottom: 0; left: 0; right: 0; background: white; padding: 15px; border-top: 1px solid #eee; display: flex; justify-content: center; }
    .form-inner { width: 100%; max-width: 600px; display: flex; gap: 10px; }
    .cmt-input { flex: 1; padding: 12px; border: 1px solid #ddd; border-radius: 20px; font-size: 14px; background: #F9FAFB; }
    .cmt-btn { background: #3182F6; color: white; border: none; padding: 0 20px; border-radius: 20px; font-weight: 600; cursor: pointer; }
  </style>
</head>
<body>
<div class="container">
  <div class="header">
    <a href="${pageContext.request.contextPath}/board/list" class="back-btn">←</a>
    <c:if test="${sessionScope.loginMember.role == 'ADMIN'}">
      <a href="${pageContext.request.contextPath}/board/delete/${post.id}" class="del-btn" onclick="return confirm('정말 삭제할까요?')">글 삭제</a>
    </c:if>
  </div>

  <c:if test="${not empty post.birthdayImgUrl}">
    <img src="${pageContext.request.contextPath}/resources/upload/${post.birthdayImgUrl}" class="post-img">
  </c:if>

  <div class="content">
    <span class="badge">${post.groupName}</span>
    <h1 class="title">${post.birthdayPersonName}</h1>
    <div class="date"><fmt:formatDate value="${post.birthdayDate}" pattern="yyyy년 MM월 dd일"/> • 조회 ${post.viewCount}</div>
    <div class="text">${post.celebrationText}</div>
  </div>

  <div class="divider"></div>

  <div class="comments-area">
    <div class="comment-title">댓글 ${comments.size()}개</div>

    <c:forEach var="cmt" items="${comments}">
      <div class="comment-item">
        <div>
          <span class="cmt-user">${cmt.nickname}</span>
          <span class="cmt-text">${cmt.commentText}</span>
        </div>
        <c:if test="${sessionScope.loginMember.userId == cmt.userId || sessionScope.loginMember.role == 'ADMIN'}">
          <a href="${pageContext.request.contextPath}/board/deleteComment?id=${cmt.id}&postId=${post.id}" class="cmt-del">x</a>
        </c:if>
      </div>
    </c:forEach>
  </div>
</div>

<div class="comment-form">
  <form action="${pageContext.request.contextPath}/board/addComment" method="post" class="form-inner">
    <input type="hidden" name="postId" value="${post.id}">
    <input type="text" name="commentText" class="cmt-input" placeholder="축하의 댓글을 남겨주세요" required>
    <button type="submit" class="cmt-btn">등록</button>
  </form>
</div>
</body>
</html>