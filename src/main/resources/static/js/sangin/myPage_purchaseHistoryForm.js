document.addEventListener("DOMContentLoaded", function () {
    axios.get("/payment-api/history")
        .then(response => {
            let paymentHistory = response.data;
            let contentSection = document.getElementById("content_section1");

            contentSection.innerHTML = '';

            paymentHistory.forEach(history => {
                const refundButton = history.refundStatus === 'y'
                    ? '<div class="pur_right_refund_btn" style="background-color: #d0d0d0">환불완료</div>'
                    : `<button class="pur_right_refund_btn" onclick="requestRefund(${history.paymentId})">환불신청</button>`;
                let reviewButton = ``;

                if (history.refundStatus === 'y') {
                    // 환불된 상태
                    reviewButton = '<div class="pur_right_review_btn" style="background-color: #d0d0d0">작성불가</div>';
                } else if (history.reviewStatus === 'y') {
                    // 이미 리뷰 작성한 상태
                    reviewButton = '<div class="pur_right_review_btn" style="background-color: #d0d0d0">작성완료</div>';
                } else {
                    // 환불되지 않았고, 리뷰도 작성하지 않은 상태
                    reviewButton = `<button class="pur_right_review_btn" onclick="requestReview(${history.classId})" style="cursor: pointer;">리뷰작성</button>`;
                }


                contentSection.innerHTML += `
                    <div class="pur_box">
                        <img src="${history.fileUrl}" class="pur_box_left">
                        <div class="pur_box_center">
                            <div class="pur_center_title">
                                <a href="/home/detail/${history.classId}">
                                    ${history.classTitle}
                                </a>
                                <div class="pur_center_teacherName">
                                강사 : ${history.teacherName}
                                </div>
                            </div>
                        </div>
                        <div class="pur_box_right">
                            <div class="pur_right_money">${history.price}원</div>
                            ${refundButton}
                            ${reviewButton}
                        </div>
                    </div>
                `;
            });
        });
});

function requestRefund(paymentId) {
    axios.post("/payment-api/refund",
        {paymentId: paymentId},
        {headers: {"Content-Type": "application/json"}}
    )
        .then(response => {
            if (response.data.status === "success") {
                alert("환불이 완료되었습니다!");
                location.reload();
            } else {
                alert(response.data.message);
            }
        })
        .catch(error => {
            console.error("환불 요청 중 오류 발생:", error.response ? error.response.data : error);
            alert("환불 중 오류가 발생하였습니다.");
        });
}

function requestReview(classId) {
    const stars = document.getElementById("review-modal-star");
    const reviewContent = document.getElementById("review-modal-content");
    const registerBtn = document.getElementById("review-modal-register-btn");
    const cancelBtn = document.getElementById("review-modal-cancel-btn");
    const reviewModal = document.getElementById("review-modal");

    let selectedRating = 0; // 사용자가 선택한 별점

    // ⭐ 별점 UI가 이미 생성되었는지 확인하고, 없으면 생성
    if (stars.children.length === 0) {
        for (let i = 1; i <= 5; i++) {
            const star = document.createElement("span");
            star.textContent = "☆";
            star.dataset.value = i;
            star.style.fontSize = "50px";
            star.style.cursor = "pointer";
            stars.appendChild(star);
        }
    }

    // ⭐ 이벤트 리스너 한 번만 추가 (중복 방지)
    stars.onmouseover = function (event) {
        if (event.target.tagName === "SPAN") {
            renderStars(event.target.dataset.value);
        }
    };

    stars.onmouseleave = function () {
        renderStars(selectedRating); // 기존 선택된 별점으로 복구
    };

    stars.onclick = function (event) {
        if (event.target.tagName === "SPAN") {
            selectedRating = event.target.dataset.value;
            renderStars(selectedRating);
        }
    };

    function renderStars(rating) {
        Array.from(stars.children).forEach(star => {
            star.textContent = star.dataset.value <= rating ? "★" : "☆";
        });
    }

    // 초기 별점 세팅
    renderStars(selectedRating);

    // 기존 이벤트 제거 후 새로 추가 (중복 방지)
    registerBtn.replaceWith(registerBtn.cloneNode(true)); // 기존 이벤트 제거를 위해 버튼을 복제 후 교체
    const newRegisterBtn = document.getElementById("review-modal-register-btn");

    newRegisterBtn.addEventListener("click", function () {
        const reviewText = reviewContent.value.trim();

        if (selectedRating === 0) {
            alert("별점을 선택해주세요!");
            return;
        }
        if (reviewText.length === 0) {
            alert("수강평을 입력해주세요!");
            return;
        }

        // 📡 백엔드로 데이터 전송
        axios.post("/api/classDetail/review", {
            classId: classId,
            rating: parseInt(selectedRating), // 숫자로 변환
            content: reviewText
        })
            .then(response => {
                alert("수강평이 성공적으로 등록되었습니다!");
                location.reload();
            })
            .catch(error => {
                console.error("❌ 수강평 등록 오류:", error);
                alert("수강평 등록 중 오류가 발생했습니다.");
            });
    });

    // 취소 버튼 이벤트 (중복 방지)
    cancelBtn.replaceWith(cancelBtn.cloneNode(true));
    const newCancelBtn = document.getElementById("review-modal-cancel-btn");

    newCancelBtn.addEventListener("click", () => {
        reviewModal.style.display = "none";
    });

    // 모달 보이기
    reviewModal.style.display = "flex";
}
