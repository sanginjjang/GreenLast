// header.js가 있으나 공통 작업으로 인한 충돌을 방지하기 위해 새로 하나 만들었습니다~
document.addEventListener("DOMContentLoaded", function () {
    // alert('test header'); 이거 됨 = js 연결은 문제없음
    const headerSearch = document.querySelector("#header_search");
    const headerBtn = document.querySelector("#header_btn");

    headerBtn.addEventListener("click", () => {
        // alert('test header');여기도 됐음
        // alert("header text = " + headerSearch.value); 이상 무
        window.location.href=`/home/homepage?keyword=${headerSearch.value}`;

    })
})