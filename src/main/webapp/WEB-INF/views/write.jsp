<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>축하 글 쓰기</title>
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
    .acrostic-btn {
      margin-top: 20px;
      padding: 14px;
      width: 100%;
      background-color: #E8F3FF;
      color: #1C6ECC;
      border: none;
      border-radius: 14px;
      font-size: 15px;
      font-weight: 700;
      cursor: pointer;
    }
    .acrostic-btn:hover {
      background-color: #D6E9FF;
    }

  </style>
</head>
<script>
  document.addEventListener('DOMContentLoaded', function () {
    const nameInput = document.querySelector('input[name="birthdayPersonName"]');
    const container = document.getElementById('acrosticContainer');
    const btn = document.getElementById('acrosticBtn');
    const form = document.querySelector('form');
    const hiddenText = document.getElementById('celebrationText');

    if (!nameInput || !container || !btn || !form || !hiddenText) {
      console.log('찾기 실패:', { nameInput, container, btn, form, hiddenText });
      return;
    }

    btn.addEventListener('click', function () {
      const name = (nameInput.value || '').trim();
      container.innerHTML = '';

      if (!name) {
        alert('먼저 생일 주인공 이름을 입력해주세요!');
        nameInput.focus();
        return;
      }

      Array.from(name).forEach(function (ch) {
        const row = document.createElement('div');
        row.style.display = 'flex';
        row.style.alignItems = 'center';
        row.style.gap = '10px';
        row.style.marginBottom = '10px';

        const label = document.createElement('span');
        label.textContent = ch + ' :';
        label.style.fontWeight = '700';
        label.style.minWidth = '32px';

        const input = document.createElement('input');
        input.type = 'text';
        input.name = 'acrosticLine';   // ✅ 여기!
        input.dataset.ch = ch;         // ✅ 여기!
        input.placeholder = ch + '(으)로 시작하는 문장';
        input.style.flex = '1';
        input.style.padding = '15px';
        input.style.borderRadius = '12px';
        input.style.border = 'none';
        input.style.backgroundColor = '#F2F4F6';
        input.style.fontSize = '15px';
        input.style.fontFamily = 'inherit';
        input.style.boxSizing = 'border-box';

        row.appendChild(label);
        row.appendChild(input);
        container.appendChild(row);
      });

      const first = container.querySelector('input');
      if (first) first.focus();
    });

    form.addEventListener('submit', function () {
      const lines = [];
      const inputs = container.querySelectorAll('input[name="acrosticLine"]');

      inputs.forEach(function (inp) {
        const ch = inp.dataset.ch || '';
        const val = (inp.value || '').trim();
        if (ch && val) lines.push(ch + ': ' + val);
      });

      hiddenText.value = lines.join('\n'); // ✅ celebration_text로 저장될 값
    });
  });
</script>




<body>
<div class="header">
  <a href="${pageContext.request.contextPath}/board/list" class="back-btn">←</a>
  <span class="page-title">축하 글 쓰기</span>
</div>

<div class="container">
  <form action="${pageContext.request.contextPath}/board/writeAction" method="post" enctype="multipart/form-data">
    <div class="card">
      <label>새새/공동체 이름</label>
      <input type="text" name="groupName" placeholder="예: 22학번 김태우 공동체" required>

      <label>생일 주인공 이름</label>
      <input type="text" name="birthdayPersonName" placeholder="누구의 생일인가요?" required>

      <label>생일 날짜</label>
      <input type="date" name="birthdayDate" required>

      <button type="button" id="acrosticBtn" class="acrostic-btn">
        ✍️ 삼행시 쓰기
      </button>
      <br>
      <br>

      <div id="acrosticContainer"></div>
      <textarea name="celebrationText" id="celebrationText" style="display:none;"></textarea>


      <label>사진 업로드</label>
      <input type="file" name="file">

      <button type="submit" class="submit-btn">등록하기</button>
    </div>
  </form>
</div>
</body>
</html>