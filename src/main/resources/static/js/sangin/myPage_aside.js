document.addEventListener('DOMContentLoaded', function () {
    axios.get('/api/mypage/getUserById')
        .then(response => {
            const user = response.data;
            alert("user.userId = " + user.userId);

            const profileImg = document.getElementById('aside_img_img');
            if (user.fileUrl) {
                profileImg.src = user.fileUrl;
                alert("이미지 경로1:" + profileImg.src);
                // 로컬 호스트로 출력
                alert("이미지 경로2:" + user.fileUrl);
                // db 저장된 값으로 출력
            }
        });

    const modal = document.querySelector("#myPageAsideModalSection");
    const btn = document.querySelector("#aside_img_btn");
    const modifyBtn = document.querySelector("#myPageAsideModal_modify");
    const cancelBtn = document.querySelector("#myPageAsideModal_cancel");
    const fileInput = document.getElementById("photoUpload");

    modal.addEventListener("click", (e) => {
        if (e.target.id == "myPageAsideModalSection") {
            modal.style.display = "none";
        }
    });
    cancelBtn.addEventListener("click", () => {
        modal.style.display = "none";
    });
    btn.addEventListener("click", () => {
        modal.style.display = "flex";
    });

    modifyBtn.addEventListener("click", () => {
        fileInput.click();
    });
    fileInput.addEventListener("change", () => {
        const file = fileInput.files[0];
        if (file) {
            const formData = new FormData();
            formData.append('file', file);

            axios.post('/api/mypage/uploadPhoto', formData, {
                headers: {'Content-Type': 'multipart/form-data'}
            })
                .then(response => {
                    document.getElementById('myPageAsideModal_img').src = response.data.imageUrl;
                    modal.style.display = "none";
                })
                .catch(error => {
                    console.error('Error uploading photo:', error);
                });
        }
    });
});
