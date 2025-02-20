// 수강평, 커뮤니티
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
                const postElement = document.createElement("div");
                postElement.classList.add("question-box");
                postElement.setAttribute("data-post-id", post.postId);

                postElement.innerHTML = `
                    <div class="question-box-top">
                        <span class="question-status">미해결</span>
                        <div class="question-title">${post.title}</div>
                        <div class="question-content">${post.content}</div>
                    </div>
                    <div class="question-box-footer">
                        <div class="question-box-footer-left">
                            <span class="question-writer">${post.username}</span>
                            <span>・</span>
                            <span class="question-created">${post.createdAt}</span>
                        </div>
                        <div class="question-box-footer-right">
                            <span class="question-comment-count">
                                <i class="fa-regular fa-message"></i> ${post.commentCount}
                            </span>
                        </div>
                    </div>
                `;

                postElement.addEventListener("click", function () {
                    openCommunityModal(post.postId);
                });

                communityDetailBox2.appendChild(postElement);
            });
        })
        .catch(error => {
            console.error("❌ 오류 발생:", error);
            communityDetailBox2.innerHTML = "<h2>게시글을 불러오는 중 오류가 발생했습니다.</h2>";
        });
});


// 게시글 모달 열기 및 상세 데이터 불러오기
// ✅ 게시글 모달 열기 및 상세 데이터 불러오기
function openCommunityModal(postId) {
    const communityModal = document.getElementById("community-modal");
    const communityModalTitle = document.getElementById("community-modal-title");
    const communityModalWriter = document.getElementById("community-modal-writer");
    const communityModalCreated = document.getElementById("community-modal-created");
    const communityModalContent = document.getElementById("community-modal-content");
    const communityCommentSection = document.getElementById("community-modal-comment-section");
    const communityModalActions = document.getElementById("community-modal-actions");

    const currentUserId = document.getElementById("currentUserId").value; // ✅ 현재 로그인한 사용자 ID 가져오기

    communityModal.setAttribute("data-post-id", postId);

    axios.get(`/api/classDetail/communityDetail`, { params: { postId: postId } })
        .then(response => {
            if (!response.data) {
                communityModalContent.innerHTML = "<p>게시글 정보를 불러올 수 없습니다.</p>";
                return;
            }

            const post = response.data;
            communityModalTitle.textContent = post.title;
            communityModalWriter.textContent = post.username;
            communityModalCreated.textContent = post.createdAt;
            communityModalContent.textContent = post.content;

            communityModalActions.innerHTML = "";

            // ✅ 현재 로그인한 사용자와 게시글 작성자가 동일하면 수정/삭제 버튼 추가
            if (String(currentUserId) === String(post.userId)) {
                communityModalActions.innerHTML = `
                    <button id="edit-post-btn" onclick="editPost(${postId})">수정</button>
                    <button id="delete-post-btn" onclick="deletePost(${postId})">삭제</button>
                `;
            }

            // ✅ 댓글 로딩
            loadComments(postId, currentUserId);

            communityModal.style.display = "flex";
        })
        .catch(error => {
            console.error("❌ 게시글 로딩 오류:", error);
            communityModalContent.innerHTML = "<p>게시글 정보를 불러오는 중 오류가 발생했습니다.</p>";
        });
}

// ✅ 댓글 로딩 함수 수정: 자신의 댓글만 수정/삭제 가능하도록 설정
function loadComments(postId, currentUserId) {
    const communityCommentSection = document.getElementById("community-modal-comment-section");

    axios.get(`/api/classDetail/communityComments`, { params: { postId: postId } })
        .then(commentResponse => {
            const comments = commentResponse.data;
            communityCommentSection.innerHTML = "";

            if (!comments || comments.length === 0) {
                communityCommentSection.innerHTML = "<p style='text-align: center; color: #888;'>등록된 댓글이 없습니다.</p>";
                return;
            }

            let commentHTML = "";
            comments.forEach(comment => {
                let editDeleteButtons = "";

                // ✅ 현재 로그인한 사용자의 ID와 댓글 작성자의 ID 비교 후 수정/삭제 버튼 추가
                if (String(currentUserId) === String(comment.userId)) {
                    editDeleteButtons = `
                        <button class="edit-comment-btn" onclick="editComment(${comment.commentId})">수정</button>
                        <button class="delete-comment-btn" onclick="deleteComment(${comment.commentId})">삭제</button>
                    `;
                }

                commentHTML += `
                    <div class="community-comment">
                        <div class="community-comment-header">
                            <span class="community-comment-writer">${comment.username}</span>
                            <span class="community-comment-createdAt">${comment.createdAt}</span>
                        </div>
                        <div class="community-comment-content" id="comment-content-${comment.commentId}">
                            ${comment.content}
                        </div>
                        <div class="community-comment-actions" style="margin-top: 10px;">
                            ${editDeleteButtons}  <!-- ✅ 자기 댓글이면 수정/삭제 버튼 추가 -->
                        </div>
                    </div>
                `;
            });

            communityCommentSection.innerHTML = commentHTML;
        })
        .catch(error => {
            console.error("❌ 댓글 로딩 오류:", error);
            communityCommentSection.innerHTML = "<p style='text-align: center; color: #888;'>댓글을 불러오는 중 오류가 발생했습니다.</p>";
        });
}



// 게시글 상세 모달 닫기 함수
function closeCommunityModal() {
    document.getElementById("community-modal").style.display = "none";
}

// 댓글 작성 모달 열기
document.getElementById("comment-register-btn").addEventListener("click", function () {
    document.getElementById("comment-modal").style.display = "flex";
});

// 댓글 작성 모달 닫기
document.getElementById("comment-modal-cancel-btn").addEventListener("click", function () {
    document.getElementById("comment-modal").style.display = "none";
});

// 댓글 등록
function registComment() {
    const content = document.getElementById("comment-modal-content").value.trim();
    const postId = document.getElementById("community-modal").getAttribute("data-post-id");

    if (!content) {
        alert("내용을 입력해주세요.");
        return;
    }

    axios.post("/api/classDetail/comment", null, { // ✅ params는 두 번째 인자가 아니라 세 번째 인자로 넣어야 함!
        params: {
            postId: postId,
            content: content
        }
    })
        .then(response => {
            alert(response.data);
            document.getElementById("comment-modal").style.display = "none";
            location.reload();
        })
        .catch(error => {
            console.error("❌ 댓글 등록 오류:", error);
            alert("댓글 등록 중 오류가 발생했습니다.");
        });
}

//              게시글 등록 모달
//              게시글 등록 모달
function registPost() {
    const classId = document.getElementById("classId").value;
    const title = document.getElementById("post-title").value.trim();
    const content = document.getElementById("post-content").value.trim();

    // ✅ 유효성 검사
    if (!title) {
        alert("제목을 입력해주세요.");
        return;
    }
    if (!content) {
        alert("내용을 입력해주세요.");
        return;
    }

    axios.post("/api/classDetail/question", null, {
        params: {
            classId: classId,
            title: title,
            content: content
        }
    })
        .then(response => {
            alert(response.data);
            closePostModal();
            location.reload();
        })
        .catch(error => {
            console.error("❌ 게시글 등록 오류:", error);
            alert("게시글 등록 중 오류가 발생했습니다.");
        });
}

// ✅ 게시글 등록 모달 열기
document.getElementById("post-register-btn").addEventListener("click", function () {
    document.getElementById("post-modal-background").style.display = "flex";
});

// ✅ 게시글 등록 모달 닫기
function closePostModal() {
    document.getElementById("post-modal-background").style.display = "none";
    document.getElementById("post-title").value = ""; // 입력 필드 초기화
    document.getElementById("post-content").value = "";
}

function editPost(postId) {
    const newTitle = prompt("새로운 제목을 입력하세요:");
    const newContent = prompt("새로운 내용을 입력하세요:");

    if (!newTitle || !newContent) {
        alert("제목과 내용을 입력해주세요.");
        return;
    }

    axios.put(`/api/classDetail/updatePost/${postId}`, {
        title: newTitle,
        content: newContent
    })
        .then(response => {
            alert("게시글이 수정되었습니다.");
            location.reload();
        })
        .catch(error => {
            console.error("❌ 게시글 수정 오류:", error);
            alert("게시글 수정 중 오류가 발생했습니다.");
        });
}


// 게시글 삭제
function deletePost(postId) {
    if (!confirm("정말 삭제하시겠습니까?")) {
        return;
    }

    axios.delete(`/api/classDetail/deletePost/${postId}`)
        .then(response => {
            alert("게시글이 삭제되었습니다.");
            location.reload();
        })
        .catch(error => {
            console.error("❌ 게시글 삭제 오류:", error);
            alert("게시글 삭제 중 오류가 발생했습니다.");
        });
}

function editComment(commentId) {
    const newContent = prompt("새로운 댓글 내용을 입력하세요:");
    if (!newContent) {
        alert("내용을 입력해주세요.");
        return;
    }

    axios.put(`/api/classDetail/updateComment/${commentId}`, {
        content: newContent
    })
        .then(response => {
            alert("댓글이 수정되었습니다.");
            location.reload(); // ✅ 성공 후 페이지 새로고침
        })
        .catch(error => {
            console.error("❌ 댓글 수정 오류:", error);
            alert("댓글 수정 중 오류가 발생했습니다.");
        });
}

function deleteComment(commentId) {
    if (!confirm("정말 삭제하시겠습니까?")) {
        return;
    }

    axios.delete(`/api/classDetail/deleteComment/${commentId}`)
        .then(response => {
            alert("댓글이 삭제되었습니다.");
            location.reload(); // ✅ 성공 후 페이지 새로고침
        })
        .catch(error => {
            console.error("❌ 댓글 삭제 오류:", error);
            alert("댓글 삭제 중 오류가 발생했습니다.");
        });
}

