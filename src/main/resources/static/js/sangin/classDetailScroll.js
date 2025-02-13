// 🔹 초기 위치 값을 전역 변수로 설정
let container2InitialOffsetTop;

window.addEventListener("load", () => {
    const container2 = document.getElementById("class-container2");
    if (container2) {
        container2InitialOffsetTop = container2.offsetTop; // 초기 위치 저장
    }
    updateScroll(); // 초기 로딩 시 적용
});

// 스크롤 위치 조정 함수
function updateScroll() {
    const container2 = document.getElementById("class-container2");
    const container3 = document.getElementById("class-container3");

    if (!container2 || !container3 || container2InitialOffsetTop === undefined) return;

    const scrollY = window.pageYOffset;

    // ✅ 컨테이너 2가 최상단에 도달하면 `fixed` 적용
    if (scrollY >= container2InitialOffsetTop) {
        container2.style.position = "fixed";
        container2.style.top = "0px";
        container2.style.backgroundColor = "rgba(250, 250, 250, 1)";
        container2.style.boxShadow = "0px 2px 5px rgba(0,0,0,0.1)";
    }
    // ✅ 원래 위치로 복귀 (relative)
    else {
        container2.style.position = "relative";
        container2.style.top = "unset";
        container2.style.backgroundColor = "white";
        container2.style.boxShadow = "none";
    }
}

// 페이지 스크롤 이벤트 등록
window.addEventListener("scroll", updateScroll);

// 🔹 클릭 시 해당 위치로 스크롤 이동
document.addEventListener("DOMContentLoaded", function () {
    document.querySelector("#container2-menu span:nth-child(1)").addEventListener("click", () => scrollToSection("detail-container-box1")); // 강의소개
    document.querySelector("#container2-menu span:nth-child(2)").addEventListener("click", () => scrollToSection("curriculum-container")); // 커리큘럼
    document.querySelector("#container2-menu span:nth-child(3)").addEventListener("click", () => scrollToSection("detail-container-box6")); // 수강평
});

// 📌 부드러운 스크롤 이동 함수
function scrollToSection(sectionId) {
    const section = document.getElementById(sectionId);
    if (section) {
        const offset = 100; // 상단 메뉴 높이를 고려한 오프셋 조정
        const targetPosition = section.getBoundingClientRect().top + window.scrollY - offset;

        window.scrollTo({
            top: targetPosition,
            behavior: "smooth" // 부드러운 스크롤 효과 추가
        });
    }
}
