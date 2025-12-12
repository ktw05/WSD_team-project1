// js/main.js

import { signup, login, getBirthdayList, getBirthdayDetail, createMessage } from './api.js';

// DOMContentLoaded 이벤트 리스너를 사용하여 페이지 로드 후 스크립트 실행
document.addEventListener('DOMContentLoaded', () => {
    // 현재 페이지의 URL 경로를 기반으로 실행할 함수를 결정합니다.
    const path = window.location.pathname;

    if (path.includes('signup.html')) {
        handleSignupPage();
    } else if (path.includes('login.html')) {
        handleLoginPage();
    } else if (path.includes('birthday_board.html')) {
        handleDetailPage();
    } else {
        // index.html (메인 페이지)
        handleIndexPage();
    }
});

// =================================================================
// 페이지별 이벤트 핸들러
// =================================================================

function handleSignupPage() {
    const form = document.getElementById('signupForm');
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const userData = {
                username: e.target.username.value,
                password: e.target.password.value,
                nickname: e.target.nickname.value,
                email: e.target.email.value
            };

            try {
                await signup(userData);
                alert('🎉 회원가입이 완료되었습니다. 로그인해주세요.');
                window.location.href = 'login.html';
            } catch (error) {
                alert(error.message);
            }
        });
    }
}

function handleLoginPage() {
    const form = document.getElementById('loginForm');
    if (form) {
        form.addEventListener('submit', async (e) => {
            e.preventDefault();
            const credentials = {
                username: e.target.username.value,
                password: e.target.password.value
            };

            try {
                const data = await login(credentials);
                // 로그인 성공 후 토큰 저장 (api.js에서 처리) 및 메인 페이지로 이동
                alert(`${data.nickname}님, 환영합니다!`);
                window.location.href = 'index.html';
            } catch (error) {
                alert(error.message);
            }
        });
    }
}

async function handleIndexPage() {
    const listContainer = document.getElementById('boardListContainer');
    if (!listContainer) return;

    // 1. 초기 게시물 목록 로드
    const loadBoards = async () => {
        try {
            const data = await getBirthdayList();
            listContainer.innerHTML = ''; // 기존 예시 카드를 지우고

            data.boards.forEach(board => {
                const card = createBoardCard(board);
                listContainer.appendChild(card);
            });
        } catch (error) {
            console.error(error);
            listContainer.innerHTML = '<p>게시물 목록을 불러오는 데 실패했습니다.</p>';
        }
    };

    // 2. 검색 및 정렬 이벤트 핸들러 (추가 구현 필요)
    const searchInput = document.getElementById('searchInput');
    const sortSelect = document.getElementById('sortSelect');

    // 간단한 검색/정렬 예시
    const applyFilter = () => {
        const params = {
            search: searchInput.value,
            sort: sortSelect.value
        };
        // TODO: params를 getBirthdayList에 전달하여 다시 로드
        loadBoards(params);
    };

    searchInput.addEventListener('change', applyFilter);
    sortSelect.addEventListener('change', applyFilter);

    loadBoards();
}

/**
 * 게시물 데이터를 기반으로 HTML 카드 요소를 생성합니다.
 */
function createBoardCard(board) {
    const article = document.createElement('article');
    article.className = 'board-card';
    article.setAttribute('data-board-id', board.id);

    // 클릭 시 상세 페이지로 이동 이벤트
    article.addEventListener('click', () => {
        window.location.href = `birthday_board.html?id=${board.id}`;
    });

    article.innerHTML = `
        <img src="${board.birthday_img_url}" alt="생일 축하 이미지" class="card-image">
        <div class="card-content">
            <h3 class="card-title">${board.birthday_person_name} 님</h3>
            <p class="card-group">작성 공동체: ${board.group_name}</p>
            <p class="card-date">생일: ${board.birthday_date}</p>
            <p class="card-views">조회수: ${board.view_count || 0}</p>
        </div>
    `;
    return article;
}

async function handleDetailPage() {
    // URL에서 boardId 추출
    const urlParams = new URLSearchParams(window.location.search);
    const boardId = urlParams.get('id');
    if (!boardId) {
        alert('잘못된 접근입니다.');
        window.location.href = 'index.html';
        return;
    }

    // 1. 상세 게시물 정보 로드 및 렌더링
    try {
        const board = await getBirthdayDetail(boardId);

        document.getElementById('boardTitle').textContent = `${board.birthday_person_name}님의 생일 보드`;
        document.getElementById('birthdayPersonName').textContent = `${board.birthday_person_name} 님`;
        document.getElementById('groupName').textContent = `작성 공동체: ${board.group_name}`;
        document.getElementById('birthdayDate').textContent = `생일: ${board.birthday_date}`;
        document.getElementById('createdAt').textContent = `작성 시점: ${new Date(board.created_at).toLocaleString()}`;
        document.getElementById('viewCount').textContent = `조회수: ${board.view_count}`;
        document.getElementById('boardImage').src = board.birthday_img_url;
        document.getElementById('celebrationText').textContent = board.celebration_text;

        // TODO: 로그인한 사용자와 게시물 작성자가 일치하면 수정/삭제 버튼 표시 로직 추가

        loadMessages(boardId); // 메시지 로드
    } catch (error) {
        alert(error.message || '게시물 정보를 불러올 수 없습니다.');
        window.location.href = 'index.html';
    }

    // 2. 메시지 작성 폼 이벤트 핸들러
    const commentForm = document.getElementById('commentForm');
    if (commentForm) {
        commentForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const commentText = document.getElementById('commentTextarea').value;

            try {
                const message = await createMessage(boardId, { comment_text: commentText });
                alert('메시지가 성공적으로 작성되었습니다.');
                document.getElementById('commentTextarea').value = '';
                addMessageToDOM(message); // 새 메시지 DOM에 추가
            } catch (error) {
                alert(error.message);
            }
        });
    }
}

// 메시지 로드 및 DOM 렌더링
async function loadMessages(boardId) {
    const commentList = document.getElementById('commentList');
    try {
        const messages = await getMessages(boardId);
        commentList.innerHTML = '';
        messages.forEach(msg => addMessageToDOM(msg));
    } catch (error) {
        commentList.innerHTML = '<p>축하 메시지를 불러오는 데 실패했습니다.</p>';
    }
}

// 메시지 하나를 DOM에 추가
function addMessageToDOM(message) {
    const commentList = document.getElementById('commentList');
    const li = document.createElement('li');
    li.className = 'comment-item';
    li.innerHTML = `
        <div class="comment-meta">
            <strong>${message.user_nickname}</strong>님 <span style="float: right;">${new Date(message.created_at).toLocaleString()}</span>
        </div>
        <p>${message.comment_text}</p>
    `;
    commentList.prepend(li); // 최신 메시지를 맨 위에 추가
}