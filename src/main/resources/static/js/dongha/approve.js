function approveClass() {
    let classId = Number(document.getElementById("classId").value);  // 🔥 숫자로 변환
    console.log("전송할 classId:", classId);  // 🔥 확인용 로그

    axios.post('/back/permitClass/approve', { classId: classId })
        .then(response => {
            console.log("서버 응답:", response);  // 🔥 서버 응답 확인
            alert('강의가 승인되었습니다!');
            window.location.href = '/admin/classView';
        })
        .catch(error => {
            console.error("요청 실패:", error.response ? error.response.data : error.message);  // 🔥 오류 상세 확인
            alert('승인 중 오류가 발생했습니다.');
        });
}

function openRejectModal() {
    document.getElementById("rejectModal").style.display = "block";
}
function closeRejectModal() {
    document.getElementById("rejectModal").style.display = "none";
}

function submitReject() {
    let classId = Number(document.getElementById("classId").value);
    let reason = document.getElementById("rejectReason").value.trim();

    if (!reason) {
        alert("반려 사유를 입력하세요!");
        return;
    }

    console.log("전송할 classId:", classId, "반려 사유:", reason);

    axios.post('/back/permitClass/reject', {
        classId: classId,
        reason: reason
    }, {
        headers: { 'Content-Type': 'application/json' }
    })
        .then(response => {
            console.log("서버 응답:", response);
            alert('강의가 반려되었습니다.');
            window.location.href = '/admin/classView';
        })
        .catch(error => {
            console.error("요청 실패:", error.response ? error.response.data : error.message);
            alert('반려 처리 중 오류가 발생했습니다.');
        });
}
