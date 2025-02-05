document.addEventListener('DOMContentLoaded', function () {
    const modal = document.querySelector("#myPageAsideModalSection");
    const btn = document.querySelector("#aside_img_btn");
    const modifyBtn = document.querySelector("#myPageAsideModal_modify");
    const cancelBtn = document.querySelector("#myPageAsideModal_cancel");
    const asideName = document.querySelector("#aside_name");
    const fileInput = document.getElementById("photoUpload");
    const asideProfileImg = document.getElementById("aside_img_img");      // 어사이드 이미지
    const modalProfileImg = document.getElementById("myPageAsideModal_img"); // 모달 이미지

    // ✅ 모달 열기
    btn.addEventListener("click", () => {
        modal.style.display = "flex";
    });

    // ✅ 모달 닫기
    modal.addEventListener("click", (e) => {
        if (e.target.id === "myPageAsideModalSection" || e.target.id === "myPageAsideModal_cancel") {
            modal.style.display = "none";
        }
    });

    // ✅ 수정 버튼 클릭 시 파일 선택창 열기
    modifyBtn.addEventListener("click", () => {
        fileInput.click();
    });

    // ✅ 파일 선택 후 자동 업로드
    fileInput.addEventListener("change", () => {
        const file = fileInput.files[0];
        if (file) {
            const formData = new FormData();
            formData.append("fileType", "profile");
            formData.append("file", file);
            formData.append("id", 1); // ✅ 사용자 ID (필요에 따라 변경)

            axios.post('/api/file/upload', formData)
                .then(response => {
                    const newImageUrl = `${response.data}?t=${new Date().getTime()}`;

                    // ✅ 모달과 어사이드에 동시 반영
                    modalProfileImg.src = newImageUrl;
                    asideProfileImg.src = newImageUrl;

                    alert("프로필 업로드 성공!");
                    getUser();
                })
                .catch(error => {
                    console.error("에러:", error);
                    alert("업로드 실패: " + (error.response ? error.response.data : error.message));
                });
        }
    });

    // ✅ 사용자 정보 불러오기 (초기 프로필 이미지 설정)
    getUser();

    function getUser(){
        axios.get('/api/mypage/getUserById')
            .then(response => {
                const user = response.data;
                asideName.textContent = user.name;
                if (user.fileUrl) {
                    asideProfileImg.src = user.fileUrl;
                    modalProfileImg.src = user.fileUrl;
                } else {
                    asideProfileImg.src = defaultImage;
                    modalProfileImg.src = defaultImage;
                }
            })
            .catch(error => {
                console.error('유저 정보 불러오기 실패:', error);
            });
    }
});

