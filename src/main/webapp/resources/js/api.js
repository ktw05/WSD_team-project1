// js/api.js

/**
 * 백엔드 서버의 기본 URL
 */
const BASE_URL = "";

// =================================================================
// 1. 인증 및 사용자 관리 API
// =================================================================

/**
 * [POST] 회원가입 요청
 * URL: /auth/signup
 * @param {object} userData - { username, password, nickname, email }
 * @returns {Promise<object>} 성공/실패 메시지
 */
export async function signup(userData) {
    try {
        const response = await fetch(`${BASE_URL}/auth/signup`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(userData)
        });

        const data = await response.json();

        if (!response.ok) {
            // 4xx, 5xx 에러 처리
            throw new Error(data.message || '회원가입에 실패했습니다.');
        }

        return data; // 성공 응답 (예: { message: "회원가입 성공" })

    } catch (error) {
        console.error('Signup API Error:', error);
        throw error;
    }
}

/**
 * [POST] 로그인 요청
 * URL: /auth/login
 * @param {object} credentials - { username, password }
 * @returns {Promise<object>} 토큰 또는 사용자 정보
 */
export async function login(credentials) {
    try {
        const response = await fetch(`${BASE_URL}/auth/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(credentials)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || '로그인에 실패했습니다. ID와 비밀번호를 확인해주세요.');
        }

        // 로그인 성공 시, 토큰을 localStorage에 저장하거나 반환
        // 예: localStorage.setItem('authToken', data.token);
        return data;

    } catch (error) {
        console.error('Login API Error:', error);
        throw error;
    }
}

/**
 * 인증 토큰을 가져오는 헬퍼 함수 (추후 인증 헤더에 사용)
 */
function getAuthHeaders() {
    const token = localStorage.getItem('authToken'); // 예시: 로컬 스토리지에서 토큰 가져오기
    return token ? {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    } : {
        'Content-Type': 'application/json'
    };
}


// =================================================================
// 2. 생일 보드 (게시물) API
// =================================================================

/**
 * [GET] 생일 보드 목록 조회 (검색/필터/정렬 지원)
 * URL: /birthdays?search=keyword&sort=latest
 * @param {object} params - { search, sort, page, size }
 * @returns {Promise<object>} 게시물 리스트 및 페이징 정보
 */
export async function getBirthdayList(params = {}) {
    const queryString = new URLSearchParams(params).toString();
    try {
        const response = await fetch(`${BASE_URL}/birthdays?${queryString}`);

        if (!response.ok) {
            throw new Error('게시물 목록을 불러오는 데 실패했습니다.');
        }
        return await response.json();
    } catch (error) {
        console.error('Get Birthday List API Error:', error);
        throw error;
    }
}

/**
 * [GET] 특정 생일 보드 상세 조회
 * URL: /birthdays/{boardId}
 * @param {number} boardId
 * @returns {Promise<object>} 상세 게시물 정보
 */
export async function getBirthdayDetail(boardId) {
    try {
        const response = await fetch(`${BASE_URL}/birthdays/${boardId}`);

        if (!response.ok) {
            throw new Error('게시물 상세 정보를 불러오는 데 실패했습니다.');
        }
        return await response.json();
    } catch (error) {
        console.error('Get Birthday Detail API Error:', error);
        throw error;
    }
}

/**
 * [POST] 생일 보드 생성
 * URL: /birthdays
 * 참고: 파일 업로드가 있으므로 FormData를 사용할 수 있습니다.
 * @param {FormData} formData - 게시물 데이터 (이미지 포함)
 */
export async function createBirthdayBoard(formData) {
    try {
        // 파일 업로드 시에는 Content-Type 헤더를 설정하지 않는 것이 일반적입니다.
        const token = localStorage.getItem('authToken');

        const response = await fetch(`${BASE_URL}/birthdays`, {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${token}` // 인증 헤더는 필요합니다.
            },
            body: formData // FormData는 JSON.stringify가 필요 없습니다.
        });

        const data = await response.json();
        if (!response.ok) {
            throw new Error(data.message || '생일 보드 생성에 실패했습니다.');
        }
        return data;

    } catch (error) {
        console.error('Create Board API Error:', error);
        throw error;
    }
}


// =================================================================
// 3. 축하 메시지 (댓글) API
// =================================================================

/**
 * [GET] 보드별 축하 메시지 목록 조회
 * URL: /birthdays/{boardId}/messages
 * @param {number} boardId
 * @returns {Promise<Array<object>>} 댓글 목록
 */
export async function getMessages(boardId) {
    try {
        const response = await fetch(`${BASE_URL}/birthdays/${boardId}/messages`);

        if (!response.ok) {
            throw new Error('축하 메시지 목록을 불러오는 데 실패했습니다.');
        }
        return await response.json();
    } catch (error) {
        console.error('Get Messages API Error:', error);
        throw error;
    }
}

/**
 * [POST] 특정 보드에 축하 메시지 작성
 * URL: /birthdays/{boardId}/messages
 * @param {number} boardId
 * @param {object} messageData - { comment_text }
 * @returns {Promise<object>} 작성된 메시지 정보
 */
export async function createMessage(boardId, messageData) {
    try {
        const response = await fetch(`${BASE_URL}/birthdays/${boardId}/messages`, {
            method: 'POST',
            headers: getAuthHeaders(),
            body: JSON.stringify(messageData)
        });

        const data = await response.json();

        if (!response.ok) {
            throw new Error(data.message || '메시지 작성에 실패했습니다. 로그인했는지 확인해주세요.');
        }
        return data;

    } catch (error) {
        console.error('Create Message API Error:', error);
        throw error;
    }
}

// ... 기타 API (PUT, DELETE, today, statistics 등) ...