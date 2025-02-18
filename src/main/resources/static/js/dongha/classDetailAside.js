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