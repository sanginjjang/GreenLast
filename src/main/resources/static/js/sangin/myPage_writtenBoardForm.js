document.addEventListener("DOMContentLoaded", function () {
    const contentSection2 = document.querySelector("#content_section2");

    // API 요청
    axios.get('/api/mypage/getWrittenBorad')
        .then(response => {
            contentSection2.innerHTML = ''; // 기존 내용 초기화
            const boardList = response.data; // 받아온 게시글 목록

            // 받아온 데이터 확인
            console.log(boardList);

            // boardList가 정상적으로 있을 경우
            boardList.forEach(board => {
                // createdAt 날짜만 포맷
                const createdAtDate = new Date(board.createdAt);
                const formattedDate = createdAtDate.toLocaleDateString('ko-KR'); // 한국형 날짜로 포맷

                // 새로운 div 요소 생성
                const section2Box = document.createElement("div");
                section2Box.classList.add("section2_box");

                section2Box.innerHTML = `
                    <div class="section2_main">
                        <div class="section2_category">자유게시판
                       
                        </div>
                        <div class="section2_title">
                        <a href="/kwanhyun/community/CommunityDetail?postId=${board.postId}">${board.title}</a>
                        </div>
                        <div class="section2_content">
                        <a href="/kwanhyun/community/CommunityDetail?postId=${board.postId}">${board.content}</a>
                        </div>
                    </div>
                    <div class="section2_footer">
                        <div class="section2_footer_left">${formattedDate}</div> <!-- 날짜만 표시 -->
                        <div class="section2_footer_right">
                            <span><i class="fa-regular fa-eye"></i>&nbsp;${board.views}</span>
                            <span><i class="fa-regular fa-message"></i>&nbsp;${board.commentCount}</span>
                        </div>
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
