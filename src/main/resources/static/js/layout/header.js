document.addEventListener("DOMContentLoaded", function() {
    const headerProfileIcon = document.getElementById("header_profile_icon");
    const headerProfileDetail = document.getElementById("header_profile_detail");
    const headerBell = document.getElementById("header_bell");
    const headerBellDetail = document.getElementById("header_bell_detail");
    const headerProfileName = document.getElementById("header_profile_name");
    const headerProfileImg = document.getElementById("header_profile_img");

    let userId = null;
    let filterType = "all";

    axios.get('/api/mypage/getUserById')
        .then(response => {
            const user = response.data;
            headerProfileName.textContent = user.name;

            if (user.fileUrl) {
                headerProfileImg.src = user.fileUrl;
            }
        })
        .catch(error => {
            console.error('유저 정보 불러오기 실패:', error);
        });


    headerProfileIcon.addEventListener("click", () => {
        // alert("클릭 테스트");
        if (headerProfileDetail.style.display === "block") {
            headerProfileDetail.style.display = "none";
        } else {
            headerProfileDetail.style.display = "block";
            headerBellDetail.style.display = "none";
        }
    });

    // [ 관현 ] 여기서부터 헤더 벨이에요~
    // 로직 : 한 아이콘 누르면 다른 아이콘 드롭다운 꺼지는거라 같은 함수에 묶음
    headerBell.addEventListener("click", () => {
        if (headerBellDetail.style.display === "block") {
            headerBellDetail.style.display = "none";
        } else {
            headerBellDetail.style.display = "block";
            headerProfileDetail.style.display = "none";

            if (userId) {
                loadAlarms(userId, filterType);
            } else {
                axios.get('/api/mypage/getUserById')
                    .then(response => {
                        userId = response.data.userId;
                        loadAlarms(userId, filterType);
                    })
                    .catch(error => console.error("유저 정보 가져오기 실패:", error));
            }
        }
    });
});

// 관현 25.02.19 알람 관련 함수 추가(알람 불러오기, 알람 클릭 시 처리, 알람 읽음처리)
async function loadAlarms(userId, filterType) {
    let alarmTotalCountSection = document.getElementById("alarmTotalCount");

    try {
        let response, countResponse

        response = await axios.get(`/api/alarm/alarms?userId=${userId}`);
        countResponse = await axios.get(`/api/alarm/alarms/countAll?userId=${userId}`);

        const alarmList = response.data;
        const alarmCount = countResponse.data;

        alarmTotalCountSection.innerHTML = `<strong>${alarmCount}개의 알림이 있습니다.</strong>`;

        updateAlarmList(alarmList, userId, filterType);

    } catch (error) {
        console.error("알람 데이터를 불러오는 중 오류 발생:", error);
    }
}

function updateAlarmList(alarmList, userId, filterType) {
    let alarmListSection = document.getElementById("alarmList");

    alarmListSection.innerHTML = "";

    if (alarmList.length === 0) {
        alarmListSection.innerHTML = "<span>새로운 알림이 없습니다.</span>";
    } else {
        alarmList.forEach(alarm => {
            let alarmItem = document.createElement("span");
            alarmItem.innerText = alarm.alarmContent;
            alarmItem.style.display = "block";
            alarmItem.style.padding = "5px 0";
            alarmItem.style.cursor = "pointer";
            alarmItem.onclick = () => handleAlarmClick(alarm, userId, filterType);
            alarmListSection.appendChild(alarmItem);
        });
    }
}

function handleAlarmClick(alarm, userId, filterType) {
    markAsRead(alarm.alarmId, userId, filterType);

    if (alarm.relatedUrl && alarm.relatedUrl.trim() !== "") {
        setTimeout(() => {
            window.location.href = alarm.relatedUrl + alarm.relatedId;
        }, 300);
    } else {
        console.log("이동할 URL이 존재하지 않음");
    }
}

function markAsRead(alarmId, userId, filterType) {
    axios.patch(`/api/alarm/read/${alarmId}`, { userId: userId })
        .then(() => {
            console.log("알림 읽음 처리 성공!");

            return axios.delete(`/api/alarm/alarms/${alarmId}?userId=${userId}`);
        })
        .then(() => {
            console.log("️ 알림 삭제 성공!");

            loadAlarms(userId, filterType);
        })
        .catch(error => console.error("알림 처리 실패:", error));
}