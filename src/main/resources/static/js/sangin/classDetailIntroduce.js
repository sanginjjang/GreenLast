document.addEventListener("DOMContentLoaded", function () {
    const detailContainerBox3 = document.getElementById("detail-container-box3");
    const classId = document.getElementById("classId").value;

    axios.get(`/api/classDetail/introduce`, { params: { classId: classId } })
        .then(response => {
            if (!response.data || response.data.length === 0) {
                detailContainerBox3.innerHTML = "<h2>등록된 강의 소개가 없습니다.</h2>";
                return;
            }

            const blocks = response.data;
            detailContainerBox3.innerHTML = "";

            blocks.forEach(block => {
                const blockElement = document.createElement("div");
                blockElement.classList.add("intro-block");

                // blockType이 single이면 한 줄 전체를 차지
                if (block.blockType === "single") {
                    blockElement.classList.add("intro-single");
                }
                // blockType이 double이면 가로로 2개씩 정렬
                else if (block.blockType === "double") {
                    blockElement.classList.add("intro-double");
                }

                block.elements.forEach(element => {
                    const elementDiv = document.createElement("div");
                    elementDiv.classList.add("intro-element");

                    if (element.elementType === "text") {
                        const textElement = document.createElement("div");
                        textElement.textContent = element.elementContent;
                        elementDiv.appendChild(textElement);
                    }
                    else if (element.elementType === "image") {
                        const imageElement = document.createElement("img");
                        imageElement.src = element.elementContent;
                        imageElement.style.maxWidth = "100%";
                        imageElement.style.height = "auto";
                        imageElement.style.borderRadius = "10px";
                        elementDiv.appendChild(imageElement);
                    }

                    blockElement.appendChild(elementDiv);
                });

                detailContainerBox3.appendChild(blockElement);
            });
        })
        .catch(error => {
            console.error("❌ 오류 발생:", error);
            detailContainerBox3.innerHTML = "<h2>강의 소개를 불러오는 중 오류가 발생했습니다.</h2>";
        });
});
