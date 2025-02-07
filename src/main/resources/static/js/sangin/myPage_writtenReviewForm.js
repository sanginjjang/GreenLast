document.addEventListener("DOMContentLoaded", function () {
    const contentSection2 = document.querySelector("#content_section2");

    // API 요청
    axios.get('/api/mypage/getWrittenReview')
        .then(response => {
            contentSection2.innerHTML = '';
            const reviewList = response.data;

            // 받아온 데이터 확인
            console.log(reviewList);

            // boardList가 정상적으로 있을 경우
            reviewList.forEach(review => {
                // createdAt 날짜만 포맷
                const createdAtDate = new Date(review.createdAt);
                const formattedDate = createdAtDate.toLocaleDateString('ko-KR');

                // 새로운 div 요소 생성
                const section2Box = document.createElement("div");
                section2Box.classList.add("section2_box");

                section2Box.innerHTML = `
                    <div class="section2_main">
                        <div class="section2_category">수강평</div>
                        <div class="section2_title">${review.content}</div>
                    </div>
                    <div class="section2_footer">
                        <div class="section2_footer_left">${formattedDate}</div> <!-- 날짜만 표시 -->
                    </div>
                `;

                // 생성한 요소를 contentSection2에 추가
                contentSection2.appendChild(section2Box);
            });
        })
        .catch(error => {
            console.error('Error updating user data:', error);
        });
});
