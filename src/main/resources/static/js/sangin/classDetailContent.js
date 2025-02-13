// 수강평, 소개, 커리큘럼 들어감
document.addEventListener("DOMContentLoaded", function () {
    const detailContainerBox3 = document.getElementById("detail-container-box3");
    const classId = document.getElementById("classId").value;

    axios.get(`/api/classDetail/introduce`, {params: {classId: classId}})
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

//커뮤니티
document.addEventListener("DOMContentLoaded", function () {
    const communityDetailBox2 = document.getElementById("community-detail-box2");
    const classId = document.getElementById("classId").value;

    axios.get(`/api/classDetail/community`, {params: {classId: classId}})
        .then(response => {
            if (!response.data || response.data.length === 0) {
                communityDetailBox2.innerHTML = "<p>등록된 게시글이 없습니다</p>";
                return;
            }
            const posts = response.data;
            communityDetailBox2.innerHTML = '';

            posts.forEach(post => {
                const data =
                `<div class="question-box">
                    <div class="question-box-top">
                    <span class="question-status">
                        미해결
                    </span>
                        <div class="question-title">
                        <a href="/kwanhyun/community/CommunityDetail?postId=${post.postId}&classId=${post.classId}">${post.title}</a>
                        </div>
                        <div class="question-content">
                        <a href="/kwanhyun/community/CommunityDetail?postId=${post.postId}&classId=${post.classId}"></a>
                        </div>
                    </div>
                    <div class="question-box-footer">
                        <div class="question-box-footer-left">
                            <span class="question-writer">${post.name}</span>
                            <span>・</span>
                            <span class="question-created">${post.createdAt}</span>
                        </div>
                        <div class="question-box-footer-right">
                            <span class="question-views">
                                <i class="fa-regular fa-eye"></i>
                                ${post.views}
                            </span>
                            <span class="question-comment-count">
                                <i class="fa-regular fa-message"></i>
                                ${post.commentCount}
                            </span>
                        </div>
                    </div>
                </div>`;

                communityDetailBox2.innerHTML += data;
            })
        })
        .catch(error => {
                console.error("❌ 오류 발생:", error);
                communityDetailBox2.innerHTML = "<h2>게시글을 불러오는 중 오류가 발생했습니다.</h2>";
            }
        );

})
