document.addEventListener("DOMContentLoaded", function () {
    updateTotalPrice();

    document.querySelectorAll('.cart-checkbox').forEach(checkbox => {
        checkbox.addEventListener("change", updateTotalPrice);
    });

    document.getElementById("select-all").addEventListener("change", function () {
        document.querySelectorAll('.cart-checkbox').forEach(checkbox => {
            checkbox.checked = this.checked;
        });
        updateTotalPrice();
    });
});

function updateTotalPrice() {
    let totalPrice = 0;
    document.querySelectorAll('.cart-checkbox:checked').forEach(checkbox => {
        const priceElement = checkbox.closest(".cart-item").querySelector(".cart-price span");
        totalPrice += parseInt(priceElement.textContent.replace(/[^0-9]/g, ''), 10);
    });
    document.getElementById("total-price").textContent = totalPrice.toLocaleString();
}

function removeFromCart(classId) {
    axios.delete(`/cart-api/remove?classId=${classId}`)
        .then(response => {
            if (response.data.success) {
                alert("장바구니에서 삭제되었습니다!");
                location.reload();
            } else {
                alert("삭제 실패: " + response.data.message);
            }
        });
}

function removeSelected() {
    let selectedItems = document.querySelectorAll('.cart-checkbox:checked');
    if (selectedItems.length === 0) {
        alert("삭제할 항목을 선택하세요!");
        return;
    }

    let deleteRequests = [];
    selectedItems.forEach(item => {
        deleteRequests.push(axios.delete(`/cart-api/remove?classId=${item.value}`));
    });

    Promise.all(deleteRequests)
        .then(() => {
            location.reload();
        });
}

function startBootpay() {
    let totalPrice = document.getElementById("total-price").textContent.replace(/[^0-9]/g, '');
    if (totalPrice == 0) {
        alert("결제할 항목이 없습니다.");
        return;
    }

    let userInfo = document.getElementById("user-info");
    let username = userInfo.getAttribute("data-username") || "알 수 없음";

    let selectedItems = Array.from(document.querySelectorAll('.cart-checkbox:checked'))
        .map(item => ({
            classId: parseInt(item.value, 10),
            price: parseInt(item.closest(".cart-item").querySelector(".cart-price span").textContent.replace(/[^0-9]/g, ''), 10)
        }));
    console.log(selectedItems);
    if (selectedItems.length === 0) {
        alert("결제할 항목을 선택하세요!");
        return;
    }

    axios.post("/payment-api/check-duplicate", { purchasedItems: selectedItems })
        .then(response => {
            if (response.data.status === "fail") {
                alert(response.data.message);
            } else {
                proceedToPayment(selectedItems, totalPrice, username);
            }
        })
        .catch(error => {
            console.error("중복 구매 확인 중 오류 발생:", error);
            alert("결제 진행 중 오류가 발생했습니다.");
        });
}

function proceedToPayment(selectedItems, totalPrice, username) {
    console.log(selectedItems);
    Bootpay.requestPayment({
        application_id: "679f11d7cc5274a3ac3fcc29",
        price: totalPrice,
        order_name: "수강 신청",
        pg: "KCP",
        method: "",
        order_id: "ORDER_" + new Date().getTime(),
        user: {
            username: username,
        },
        items: selectedItems.map(item => ({
            id: item.classId,
            name: item.classTitle || "강의 패키지",
            qty: 1,
            price: item.price
        })),
        extra: {
            sandbox: true,
            methods: ["card", "phone", "vbank", "account", "kakaopay", "tosspay", "naverpay", "payco"]
        }
    }).then(response => {
        console.log("🔥 결제 성공 응답 전체:", response);  // 이 부분 확인 필수!!
        const receiptId = response.data ? response.data.receipt_id : response.receipt_id;  // 구조에 따라 가져오기
        console.log("결제한 receiptId: " + receiptId);

        if (!receiptId) {
            console.error("🔥 receiptId가 undefined입니다!");
            alert("결제는 성공했지만, 결제 내역 저장에 실패했습니다. 관리자에게 문의하세요.");
            return;
        }

        axios.post("/payment-api/payment", {
            purchasedItems: selectedItems.map(item => ({
                classId: item.classId,
                price: item.price,
                receiptId: receiptId
            }))
        }, {
            headers: {
                "Content-Type": "application/json"
            }
        }).then(response => {
            alert("결제가 완료되었습니다!");
            removeSelected(selectedItems);
        }).catch(error => {
            console.error("결제 내역 저장 실패:", error.response ? error.response.data : error);
        });
    }).catch(error => {
        console.error("결제 실패:", error);
        alert("결제가 취소되었습니다.");
    });
}

function savePayment(selectedItems) {
    axios.post("/payment-api/payment", { purchasedItems: selectedItems }, {
        headers: { "Content-Type": "application/json" }
    })
        .then(response => {
            alert("결제가 완료되었습니다!");
            let deleteRequests = selectedItems.map(item =>
                axios.delete(`/cart-api/remove?classId=${item.classId}`)
            );

            Promise.all(deleteRequests)
                .then(() => location.reload());
        })
        .catch(error => {
            console.error("결제 내역 저장 실패:", error.response ? error.response.data : error);
        });
}