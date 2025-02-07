document.addEventListener("DOMContentLoaded", function () {
    axios.get("/payment-api/history")
        .then(response => {
            let paymentHistory = response.data;
            let contentSection = document.getElementById("content_section1");

            contentSection.innerHTML = '';
            paymentHistory.forEach(history => {

                contentSection.innerHTML += `
                    <div class="pur_box">
                        <img src="${history.fileUrl}" class="pur_box_left">
                        <div class="pur_box_center">
                            <div class="pur_center_title">
                                <a href="/home/detail/${history.classId}">
                                    ${history.classTitle}
                                </a>
                            </div>
                        </div>
                        <div class="pur_box_right">
                            <div class="pur_right_money">${history.price}원</div>
                            <div class="pur_right_delete">
                                <a href="/api/mypage/purchaseHistory/delete/pno?pno=${history.paymentId}">내역삭제</a>
                            </div>
                            <div class="pur_right_refund">환불신청</div>
                        </div>
                    </div>
                `;
            });
        })
        .catch(error => {
            console.error("결제 내역을 불러오는 중 오류 발생:", error);
        });
});
