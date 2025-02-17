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

document.addEventListener("DOMContentLoaded", function () {
    const communityDetailBox2 = document.getElementById("community-detail-box2");
    const classId = document.getElementById("classId").value;
    const communityModal = document.getElementById("community-modal");
    const communityModalContent = document.getElementById("community-modal-content");

    axios.get(`/api/classDetail/community`, {params: {classId: classId}})
        .then(response => {
            if (!response.data || response.data.length === 0) {
                communityDetailBox2.innerHTML = "<p>등록된 게시글이 없습니다</p>";
                return;
            }
            const posts = response.data;
            communityDetailBox2.innerHTML = '';

            posts.forEach(post => {
                const postElement = document.createElement("div");
                postElement.classList.add("question-box");
                postElement.innerHTML = `
                    <div class="question-box-top">
                        <span class="question-status">미해결</span>
                        <div class="question-title">
                            <div class="post-link" data-post-id="${post.postId}" data-class-id="${post.classId}">${post.title}</div>
                        </div>
                        <div class="question-content">${post.content}</div>
                    </div>
                    <div class="question-box-footer">
                        <div class="question-box-footer-left">
                            <span class="question-writer">${post.name}</span>
                            <span>・</span>
                            <span class="question-created">${post.createdAt}</span>
                        </div>
                        <div class="question-box-footer-right">
<!--                            <span class="question-views">-->
<!--                                <i class="fa-regular fa-eye"></i> ${post.views}-->
<!--                            </span>-->
                            <span class="question-comment-count">
                                <i class="fa-regular fa-message"></i> ${post.commentCount}
                            </span>
                        </div>
                    </div>
                `;

                postElement.querySelector(".post-link").addEventListener("click", function (event) {
                    event.preventDefault();
                    const postId = this.getAttribute("data-post-id");
                    openCommunityModal(postId);
                });

                communityDetailBox2.appendChild(postElement);
            });
        })
        .catch(error => {
            console.error("❌ 오류 발생:", error);
            communityDetailBox2.innerHTML = "<h2>게시글을 불러오는 중 오류가 발생했습니다.</h2>";
        });
});

// ✅ 모달 열기 및 상세 데이터 불러오기
function openCommunityModal(postId) {
    const communityModal = document.getElementById("community-modal");
    const communityModalTitle = document.getElementById("community-modal-title");
    const communityModalWriter = document.getElementById("community-modal-writer");
    const communityModalCreated = document.getElementById("community-modal-created");
    const communityModalContent = document.getElementById("community-modal-content");
    const communityCommentSection = document.getElementById("community-modal-comment-section");

    axios.get(`/api/classDetail/communityDetail`, { params: { postId: postId } })
        .then(response => {
            if (!response.data) {
                communityModalContent.innerHTML = "<p>게시글 정보를 불러올 수 없습니다.</p>";
                return;
            }

            const post = response.data;

            // ✅ 기존 HTML 구조에 데이터 주입
            communityModalTitle.textContent = post.title;
            communityModalWriter.textContent = post.writer;
            communityModalCreated.textContent = post.createdAt;
            communityModalContent.textContent = post.content;

            // ✅ 댓글 목록 불러오기
            axios.get(`/api/classDetail/communityComments`, { params: { postId: postId } })
                .then(commentResponse => {
                    const comments = commentResponse.data;
                    communityCommentSection.innerHTML = ""; // 기존 댓글 초기화

                    if (!comments || comments.length === 0) {
                        communityCommentSection.innerHTML = "<p style='text-align: center; color: #888;'>등록된 댓글이 없습니다.</p>";
                        return;
                    }

                    let commentHTML = "";
                    comments.forEach(comment => {
                        commentHTML += `
                            <div class="community-comment">
                                <div style="display: flex; align-items: center; gap: 10px; padding-top: 12px; padding-left: 12px;">
                                    <div class="community-comment-writer">${comment.writer}</div>
                                    <div class="community-comment-createdAt">${comment.createdAt}</div>
                                </div>
                                <div class="community-comment-content">${comment.content}</div>
                            </div>
                        `;
                    });

                    communityCommentSection.innerHTML = commentHTML;
                })
                .catch(error => {
                    console.error("❌ 댓글 로딩 오류:", error);
                    communityCommentSection.innerHTML = "<p style='text-align: center; color: #888;'>댓글을 불러오는 중 오류가 발생했습니다.</p>";
                });

            // ✅ 모달 열기
            communityModal.style.display = "flex";
        })
        .catch(error => {
            console.error("❌ 게시글 로딩 오류:", error);
            communityModalContent.innerHTML = "<p>게시글 정보를 불러오는 중 오류가 발생했습니다.</p>";
        });
}

// ✅ 모달 닫기 함수
function closeCommunityModal() {
    document.getElementById("community-modal").style.display = "none";
}



function registComment(){

}

// 댓글 작성 버튼 클릭 시 모달 열기
document.getElementById("comment-register-btn").addEventListener("click", function () {
    document.getElementById("comment-modal").style.display = "flex";
});

// 취소 버튼 클릭 시 모달 닫기
document.getElementById("comment-modal-cancel-btn").addEventListener("click", function () {
    document.getElementById("comment-modal").style.display = "none";
});
















document.addEventListener("DOMContentLoaded", function () {
    const chevronIcons = document.querySelectorAll(".curriculum-section-chevron");
    const allCollapseButton = document.getElementById("curriculum-detail1-all-btn");
    const allLessons = document.querySelectorAll(".curriculum-lesson");

    chevronIcons.forEach(icon => {
        icon.addEventListener("click", function () {
            const section = this.closest(".curriculum-box");
            const lessons = section.querySelectorAll(".curriculum-lesson");

            let isVisible = false;
            lessons.forEach(lesson => {
                if (lesson.style.display !== "none") {
                    //  display 가 none이 아니다? = flex = 보인다
                    isVisible = true;
                }
            });

            lessons.forEach(lesson => {
                lesson.style.display = isVisible ? "none" : "flex";
            });

            this.innerHTML = isVisible
                ? '<i class="fa-solid fa-chevron-down"></i>'
                : '<i class="fa-solid fa-chevron-up"></i>';
        });
    });

    allCollapseButton.addEventListener("click", function () {
        let isAllVisible = false; // 모든 lesson이 닫혀 있다고 가정

        allLessons.forEach(lesson => {
            if (lesson.style.display !== "none") {
                isAllVisible = true; // 하나라도 보이면 true
            }
        });

        // 모두 보이면 다 숨기고, 다 숨겨져 있으면 다 보이게 하기
        if (isAllVisible) {
            allLessons.forEach(lesson => lesson.style.display = "none");
            chevronIcons.forEach(icon => icon.innerHTML = '<i class="fa-solid fa-chevron-down"></i>');
            allCollapseButton.textContent = "모두 펼치기"; // 버튼 텍스트 변경
        } else {
            allLessons.forEach(lesson => lesson.style.display = "flex");
            chevronIcons.forEach(icon => icon.innerHTML = '<i class="fa-solid fa-chevron-up"></i>');
            allCollapseButton.textContent = "모두 접기"; // 버튼 텍스트 변경
        }
    });
});


function addToCart() {
    const classId = document.getElementById("addToCartButton").getAttribute("data-class-id");

    axios.post('/cart-api/add', {classId: classId})
        .then(response => {
            const data = response.data;
            alert(data.message);
            if (data.success) {
                window.location.href = "/cart";
            }
        })
        .catch(error => {
            console.error('에러 발생:', error);
            alert('장바구니 추가 중 오류가 발생했습니다!');
        });
}

function addCart() {
    const classId = document.getElementById("addToCartButton").getAttribute("data-class-id");

    axios.post('/cart-api/add', {classId: classId})
        .then(response => {
            const data = response.data;
            alert(data.message);
        })
        .catch(error => {
            console.error('에러 발생:', error);
            alert('장바구니 추가 중 오류가 발생했습니다!');
        });
}

