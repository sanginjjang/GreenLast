document.addEventListener("DOMContentLoaded", function () {
    const classId = document.getElementById("classId").value;
    const reviewContainer = document.getElementById("detail-container-box8");

    // ⭐ 추가: 동적으로 업데이트할 요소 찾기
    const avgRatingScore = document.getElementById("average-rating-score");
    const avgRatingStars = document.getElementById("average-rating-stars");
    const totalReviewsCount = document.getElementById("total-reviews-count");

    // ⭐ 추가: 강의 상단에도 적용
    const dynamicStars = document.getElementById("dynamic-stars");
    const dynamicScore = document.getElementById("dynamic-score");
    const dynamicReviewCount = document.getElementById("dynamic-review-count");

    axios.get(`/api/classDetail/review`, { params: { classId: classId } })
        .then(response => {
            if (response.status === 204) { // 204 No Content 처리
                reviewContainer.innerHTML = "<p>등록된 리뷰가 없습니다.</p>";
                avgRatingScore.innerText = "0.0";
                avgRatingStars.innerHTML = "☆☆☆☆☆";
                totalReviewsCount.innerText = "수강평 0개";

                // ⭐ 상단 영역도 0으로 초기화
                dynamicScore.innerText = "( 0.0 )";
                dynamicStars.innerHTML = "☆☆☆☆☆";
                dynamicReviewCount.innerText = "수강평 0개";

                return;
            }

            const reviews = response.data; // 받아온 리뷰 데이터 리스트
            reviewContainer.innerHTML = ""; // 기존 내용 제거 후 새로운 리뷰 추가

            // ⭐ 평균 평점 및 총 리뷰 개수 계산
            let totalRating = 0;
            reviews.forEach(review => totalRating += review.rating);
            const avgRating = reviews.length > 0 ? (totalRating / reviews.length).toFixed(1) : "0.0";

            // ⭐ 별 개수 계산 (평균 평점)
            const filledStars = "★".repeat(Math.floor(avgRating));
            const emptyStars = "☆".repeat(5 - Math.floor(avgRating));
            const starDisplay = filledStars + emptyStars;

            // ⭐ HTML 업데이트 (강의 상세 수강평 영역)
            avgRatingScore.innerText = avgRating; // "4.5"
            avgRatingStars.innerHTML = starDisplay; // "★★★★☆"
            totalReviewsCount.innerText = `수강평 ${reviews.length}개`; // "수강평 178개"

            // ⭐ 강의 상단 영역 업데이트
            dynamicScore.innerText = `( ${avgRating} )`;
            dynamicStars.innerHTML = starDisplay;
            dynamicReviewCount.innerText = `수강평 ${reviews.length}개`;

            // ⭐ 개별 리뷰 추가
            reviews.forEach(review => {
                const filledStars = "★".repeat(Math.floor(review.rating));
                const emptyStars = "☆".repeat(5 - Math.floor(review.rating));
                const starDisplay = filledStars + emptyStars;

                const reviewHTML = `
                    <div class="box8-review">
                        <div class="box8-review-top">
                            <img src="${review.fileUrl}" class="box8-review-profile">
                            <div>
                                <div class="box8-review-name">${review.name}</div>
                                <div class="box8-review-createdAt" style="color: #00b0a2">${review.createdAt}</div>
                            </div>
                        </div>
                        <div class="box8-review-center">
                            <div class="box8-review-star" style="color: gold;">${starDisplay}</div>
                            <div class="box8-review-rating">${review.rating.toFixed(1)}</div>
                        </div>
                        <div class="box8-review-bottom">
                            ${review.content}
                        </div>
                    </div>
                `;
                reviewContainer.innerHTML += reviewHTML;
            });
        })
        .catch(error => {
            console.error("❌ 오류 발생:", error);
            reviewContainer.innerHTML = "<p>리뷰를 불러오는 중 오류가 발생했습니다.</p>";

            // ⭐ 오류 발생 시 기본값 적용
            avgRatingScore.innerText = "0.0";
            avgRatingStars.innerHTML = "☆☆☆☆☆";
            totalReviewsCount.innerText = "수강평 0개";

            dynamicScore.innerText = "( 0.0 )";
            dynamicStars.innerHTML = "☆☆☆☆☆";
            dynamicReviewCount.innerText = "수강평 0개";
        });
});
