// 🔹 초기 위치 값을 전역 변수로 설정
let container2InitialOffsetTop;

window.addEventListener("load", () => {
    const container2 = document.getElementById("class-container2");
    if (container2) {
        container2InitialOffsetTop = container2.offsetTop; // 초기 위치 저장
    }
    updateScroll(); // 초기 로딩 시 적용
});

function updateScroll() {
    const container2 = document.getElementById("class-container2");
    const container3 = document.getElementById("class-container3");

    if (!container2 || !container3 || container2InitialOffsetTop === undefined) return;

    const container2Top = container2.getBoundingClientRect().top;
    const container3Top = container3.getBoundingClientRect().top;
    const scrollY = window.pageYOffset;

    console.log(`scrollY: ${scrollY}, container2Top: ${container2Top}, container2InitialOffsetTop: ${container2InitialOffsetTop}, container3Top: ${container3Top}`);

    if (scrollY >= container2InitialOffsetTop && container3Top > 60) {
        console.log("🚀 Fixed 적용");
        container2.style.position = "fixed";
        container2.style.top = "0px";
        container2.style.right = "250px";
        container2.style.backgroundColor = "rgba(250, 250, 250, 1)"; // 배경 강조
        container2.style.boxShadow = "0px 2px 5px rgba(0,0,0,0.1)"; // 그림자 추가
    }
    else if (scrollY < container2InitialOffsetTop) {
        console.log("🔄 Relative 적용 (원래 위치 복귀)");
        container2.style.position = "relative";
        container2.style.top = "unset";
        container2.style.right = "250px";
        container2.style.backgroundColor = "white"; // 원래 배경 복구
        container2.style.boxShadow = "none"; // 그림자 제거
    }
}

// 페이지 스크롤 이벤트 등록
window.addEventListener("scroll", updateScroll);
