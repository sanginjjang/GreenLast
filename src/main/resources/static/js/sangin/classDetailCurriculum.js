document.addEventListener("DOMContentLoaded", function () {
    const curriculumDetail2 = document.getElementById("curriculum-detail2");
    const allCollapseButton = document.getElementById("curriculum-detail1-all-btn");
    const totalNumber = document.getElementById("curriculum-detail1-top-right-number");
    const classId = document.getElementById("classId").value;

    axios.get(`/api/classDetail/curriculum`, { params: { classId: classId } })
        .then(response => {
            if (!response.data || response.data.length === 0) {
                curriculumDetail2.innerHTML = "<p>등록된 커리큘럼이 없습니다.</p>";
                return;
            }

            curriculumDetail2.innerHTML = ""; // 기존 데이터 초기화
            const sections = response.data;
            totalNumber.textContent = '';
            let lessonNumber = 0;

            sections.forEach(section => {
                let sectionHTML = `
                    <div class="curriculum-box" data-section-id="${section.sectionId}">
                        <div class="curriculum-section">
                            <div class="curriculum-section-left">
                                <div class="curriculum-section-chevron" data-section-id="${section.sectionId}">
                                    <i class="fa-solid fa-chevron-down"></i>
                                </div>
                                <div class="curriculum-section-title">${section.sectionTitle}</div>
                            </div>
                            <div class="curriculum-section-right">
                                <div class="curriculum-section-number">${section.lessonDTOList.length}개</div>
                                <div>∙</div>
                                <div class="curriculum-section-time">${section.totalTime ? section.totalTime : "4시간 26분"}</div>
                            </div>
                        </div>
                        <div class="curriculum-lesson-list" data-section-id="${section.sectionId}" style="display: none;">
                `;

                section.lessonDTOList.forEach(lesson => {
                    lessonNumber ++;
                    sectionHTML += `
                        <div class="curriculum-lesson">
                            <div class="curriculum-lesson-title">
                                <a th:href="@{/${lesson.fileUrl}">
                                <i class="fa-solid fa-play"></i>&nbsp;
                                ${lesson.lessonTitle}
                                </a>
                            </div>
                            <div class="curriculum-lesson-time">${lesson.duration ? lesson.duration : "00:00"}</div>
                        </div>`;
                });

                sectionHTML += `</div></div>`; // ✅ lesson-list 및 section 닫기
                curriculumDetail2.innerHTML += sectionHTML;
            });
            totalNumber.textContent = lessonNumber + "개∙ 4시간 26분";

            setToggleEventListeners();
        })
        .catch(error => {
            console.error("❌ 커리큘럼 로딩 오류:", error);
            curriculumDetail2.innerHTML = "<p>커리큘럼 정보를 불러오는 중 오류가 발생했습니다.</p>";
        });

    function setToggleEventListeners() {
        document.querySelectorAll(".curriculum-section-chevron").forEach(chevron => {
            chevron.addEventListener("click", function () {
                const sectionId = this.getAttribute("data-section-id");
                const lessonList = document.querySelector(`.curriculum-lesson-list[data-section-id="${sectionId}"]`);

                let isVisible = lessonList.style.display === "flex";
                lessonList.style.display = isVisible ? "none" : "flex";

                // 아이콘 변경
                this.innerHTML = isVisible
                    ? '<i class="fa-solid fa-chevron-down"></i>'
                    : '<i class="fa-solid fa-chevron-up"></i>';
            });
        });
    }

    allCollapseButton.addEventListener("click", function () {
        let allLessonLists = document.querySelectorAll(".curriculum-lesson-list");
        let isAllVisible = Array.from(allLessonLists).some(list => list.style.display !== "none");

        allLessonLists.forEach(list => {
            list.style.display = isAllVisible ? "none" : "flex";
        });

        document.querySelectorAll(".curriculum-section-chevron").forEach(chevron => {
            chevron.innerHTML = isAllVisible
                ? '<i class="fa-solid fa-chevron-down"></i>'
                : '<i class="fa-solid fa-chevron-up"></i>';
        });

        allCollapseButton.textContent = isAllVisible ? "모두 펼치기" : "모두 접기";
    });
});
