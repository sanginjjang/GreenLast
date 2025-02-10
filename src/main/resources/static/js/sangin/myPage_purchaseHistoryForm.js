document.addEventListener("DOMContentLoaded", function () {
    axios.get("/payment-api/history")
        .then(response => {
            let paymentHistory = response.data;
            let contentSection = document.getElementById("content_section1");

            contentSection.innerHTML = '';

            paymentHistory.forEach(history => {
                const refundButton = history.refundStatus === '환불완료'
                    ? '<div class="pur_right_refund">환불완료</div>'
                    : `<button class="pur_right_refund_btn" onclick="requestRefund(${history.paymentId})">환불신청</button>`;

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
                            ${refundButton}
                        </div>
                    </div>
                `;
            });
        })
        .catch(error => {
            console.error("결제 내역을 불러오는 중 오류 발생:", error);
        });
});

function requestRefund(paymentId) {
    console.log("환불 요청하는 결제 ID:", paymentId);  // 🔥 결제 ID 출력 확인

    axios.post("/payment-api/refund",
        { paymentId: paymentId },  // JSON 형식으로 보냄
        { headers: { "Content-Type": "application/json" } }  // 헤더 설정 확인!!!
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
            alert("구매 후 3시간이 지나 환불이 불가합니다.");
        });
}
