<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>관리자 페이지</title>
  <style>
    body { margin: 0; background-color: #F2F4F6; font-family: -apple-system, BlinkMacSystemFont, "Apple SD Gothic Neo", sans-serif; }
    .header { background: #191F28; padding: 15px 20px; display: flex; justify-content: space-between; align-items: center; color: white; }
    .logo { font-weight: 700; font-size: 18px; color: white; text-decoration: none; }
    .home-btn { font-size: 14px; color: #bbb; text-decoration: none; }

    .container { max-width: 800px; margin: 30px auto; padding: 0 20px; }
    .card { background: white; border-radius: 16px; padding: 20px; box-shadow: 0 4px 20px rgba(0,0,0,0.05); }
    h2 { margin-top: 0; color: #191F28; }

    table { width: 100%; border-collapse: collapse; margin-top: 20px; font-size: 14px; }
    th { text-align: left; color: #8B95A1; padding: 10px; border-bottom: 1px solid #eee; }
    td { padding: 15px 10px; border-bottom: 1px solid #f5f5f5; color: #333; }

    .role-badge { padding: 4px 8px; border-radius: 4px; font-size: 12px; font-weight: 700; }
    .role-user { background: #F2F4F6; color: #4E5968; }
    .role-admin { background: #E8F3FF; color: #3182F6; }

    .kick-btn { background: #FF3B30; color: white; border: none; padding: 6px 12px; border-radius: 6px; cursor: pointer; text-decoration: none; font-size: 12px; }
    .kick-btn:hover { background: #D32F2F; }
  </style>
</head>
<body>
<div class="header">
  <div class="logo">ADMIN PAGE 🔒</div>
  <a href="${pageContext.request.contextPath}/board/list" class="home-btn">메인으로 돌아가기</a>
</div>

<div class="container">
  <div class="card">
    <h2>회원 관리</h2>
    <p>총 회원 수: ${members.size()}명</p>

    <table>
      <thead>
      <tr>
        <th>ID</th>
        <th>이름(닉네임)</th>
        <th>이메일</th>
        <th>권한</th>
        <th>관리</th>
      </tr>
      </thead>
      <tbody>
      <c:forEach var="m" items="${members}">
        <tr>
          <td>${m.username}</td>
          <td>${m.nickname}</td>
          <td>${m.email}</td>
          <td>
                                <span class="role-badge ${m.role == 'ADMIN' ? 'role-admin' : 'role-user'}">
                                    ${m.role}
                                </span>
          </td>
          <td>
            <c:if test="${m.username != 'admin'}">
              <a href="${pageContext.request.contextPath}/admin/deleteMember?id=${m.userId}"
                 class="kick-btn"
                 onclick="return confirm('${m.nickname} 회원을 정말 강제 탈퇴시키겠습니까?')">추방</a>
            </c:if>
          </td>
        </tr>
      </c:forEach>
      </tbody>
    </table>
  </div>
</div>
</body>
</html>