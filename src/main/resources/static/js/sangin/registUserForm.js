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
