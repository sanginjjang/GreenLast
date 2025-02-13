document.addEventListener("DOMContentLoaded", function () {
    const detailContainerBox3 = document.getElementById("detail-container-box3");
    const classId = document.getElementById("classId").value;

    axios.get(`/api/classDetail/introduce`, { params: { classId: classId } })
        .then(response => {
            if (!response.data || response.data.length === 0) { // 데이터가 없는 경우 처리
                detailContainerBox3.innerHTML = "<h2>등록된 강의 소개가 없습니다.</h2>";
                return;
            }

            const introduces = response.data;
            detailContainerBox3.innerHTML = "";

            introduces.forEach(intro => {
                const introElement = document.createElement("div");
                introElement.classList.add("intro-content");

                // 텍스트 내용 추가
                const textElement = document.createElement("p");
                textElement.textContent = intro.introText;
                introElement.appendChild(textElement);

                // 이미지가 있을 경우 추가
                if (intro.fileUrl) {
                    const imageElement = document.createElement("img");
                    imageElement.src = intro.fileUrl;
                    imageElement.alt = "강의 소개 이미지";
                    imageElement.style.maxWidth = "700px";
                    imageElement.style.height = "auto";
                    imageElement.style.borderRadius = "10px";
                    imageElement.style.marginTop = "10px";
                    introElement.appendChild(imageElement);
                }

                detailContainerBox3.appendChild(introElement);
            });
        })
        .catch(error => {
            console.error("❌ 오류 발생:", error);
            detailContainerBox3.innerHTML = "<h2>강의 소개를 불러오는 중 오류가 발생했습니다.</h2>";
        });
});

document.addEventListener("DOMContentLoaded",function (){

})
