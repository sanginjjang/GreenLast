document.addEventListener("DOMContentLoaded", function() {
    const headerProfileIcon = document.getElementById("header_profile_icon");
    const headerProfileDetail = document.getElementById("header_profile_detail");
    const headerBell = document.getElementById("header_bell");
    const headerBellDetail = document.getElementById("header_bell_detail");

    const headerProfileName = document.getElementById("header_profile_name");
    const headerProfileImg = document.getElementById("header_profile_img");
    axios.get('/api/mypage/getUserById')
        .then(response => {
            const user = response.data;
            headerProfileName.textContent = user.name;

            if (user.fileUrl) {
                headerProfileImg.src = user.fileUrl;
            }
        })
        .catch(error => {
            console.error('유저 정보 불러오기 실패:', error);
        });


    headerProfileIcon.addEventListener("click", () => {
        // alert("클릭 테스트");
        if (headerProfileDetail.style.display === "block") {
            headerProfileDetail.style.display = "none";
        } else {
            headerProfileDetail.style.display = "block";
            headerBellDetail.style.display = "none";
        }
    });

    // [ 관현 ] 여기서부터 헤더 벨이에요~
    // 로직 : 한 아이콘 누르면 다른 아이콘 드롭다운 꺼지는거라 같은 함수에 묶음
    headerBell.addEventListener("click", () => {
        if (headerBellDetail.style.display === "block") {
            headerBellDetail.style.display = "none";
        } else {
            headerBellDetail.style.display = "block";
            headerProfileDetail.style.display = "none";
        }
    });
});

