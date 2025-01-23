// DOM 요소 가져오기
const yearSelect = document.getElementById('inputYear');
const monthSelect = document.getElementById('inputMonth');
const daySelect = document.getElementById('inputDay');

// 연도 생성 (현재 연도 기준)
const currentYear = new Date().getFullYear();
for (let i = currentYear; i >= 1900; i--) {
    const option = document.createElement('option');
    option.value = i;
    option.textContent = i;
    yearSelect.appendChild(option);
}

// 월 생성
for (let i = 1; i <= 12; i++) {
    const option = document.createElement('option');
    option.value = i;
    option.textContent = i;
    monthSelect.appendChild(option);
}

// 일 동적 생성 함수
function updateDays() {
    const selectedYear = parseInt(yearSelect.value, 10) || currentYear;
    const selectedMonth = parseInt(monthSelect.value, 10);

    // 월이 선택되지 않으면 반환
    if (!selectedMonth) return;

    // 해당 월의 마지막 날짜 계산
    const lastDay = new Date(selectedYear, selectedMonth, 0).getDate();

    // 일 초기화
    daySelect.innerHTML = '';
    for (let i = 1; i <= lastDay; i++) {
        const option = document.createElement('option');
        option.value = i;
        option.textContent = i;
        daySelect.appendChild(option);
    }
}

// 월 변경 시 일 업데이트
monthSelect.addEventListener('change', updateDays);

// 초기 설정
updateDays(); // 초기화 시 첫 월의 날짜 설정

// 아이디 유효성 검사 함수
function validateUserId(userId) {
    // 1. 공백 체크
    if (/\s/.test(userId)) {
        return "아이디에 공백이 포함될 수 없습니다.";
    }

    // 2. 길이 체크 (8자 이상 32자 이하)
    if (userId.length < 8 || userId.length > 32) {
        return "아이디는 8자 이상, 32자 이하여야 합니다.";
    }

    // 3. 영문, 숫자, 특수문자 중 2가지 이상 포함
    const hasLetter = /[a-zA-Z]/.test(userId); // 영문 포함 여부
    const hasNumber = /\d/.test(userId);      // 숫자 포함 여부
    const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(userId); // 특수문자 포함 여부

    const validCharTypes = [hasLetter, hasNumber, hasSpecialChar].filter(Boolean).length;
    if (validCharTypes < 2) {
        return "아이디는 영문/숫자/특수문자 중 2가지 이상을 포함해야 합니다.";
    }

    // 4. 연속된 문자/숫자 3개 이상 방지
    if (/(\w)\1\1/.test(userId)) {
        return "아이디에 연속된 동일 문자/숫자가 3번 이상 포함될 수 없습니다.";
    }

    // 모든 조건을 만족하면 null 반환
    return "모든 조건을 만족합니다";
}

// 아이디 유효성 검사 실행
function checkUserId() {
    const userId = document.getElementById("inputId").value;
    const errorMessage = validateUserId(userId); // 유효성 검사 함수 호출

    const errorDisplay = document.getElementById("id_error_message");
    if (errorMessage) {
        errorDisplay.textContent = errorMessage; // 에러 메시지 출력
    } else {
        errorDisplay.textContent = ""; // 유효하면 에러 메시지 제거
    }
}

// 중복검사 버튼 클릭 이벤트 (기본 틀만 작성)
function checkDuplicateId() {
    const userId = document.getElementById("inputId").value;

    if (!userId) {
        alert("아이디를 입력하세요.");
        return;
    }

    axios.get(`/api/check-duplicate-id?userId=${userId}`)
        .then(response => {
            console.log(response.data); // { duplicate: true, message: "..." }
            if (response.data.duplicate) {
                alert(response.data.message); // "이미 사용 중인 아이디입니다."
            } else {
                alert(response.data.message); // "사용 가능한 아이디입니다."
            }
        })
        .catch(error => {
            console.error("오류 발생:", error);
        });

}

