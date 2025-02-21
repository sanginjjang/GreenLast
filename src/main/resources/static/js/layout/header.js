document.addEventListener("DOMContentLoaded", function () {
    const headerProfileIcon = document.getElementById("header_profile_icon");
    const headerProfileDetail = document.getElementById("header_profile_detail");
    const headerBell = document.getElementById("header_bell");
    const headerBellDetail = document.getElementById("header_bell_detail");
    const headerProfileName = document.getElementById("header_profile_name");
    const headerProfileImg = document.getElementById("header_profile_img");

    let userId = null;

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
                loadAlarms(userId);
            } else {
                axios.get('/api/mypage/getUserById')
                    .then(response => {
                        userId = response.data.userId;
                        loadAlarms(userId);
                    })
                    .catch(error => console.error("유저 정보 가져오기 실패:", error));
            }
        }
    });
});


// 관현 25.02.19 알람 관련 함수 추가(알람 불러오기, 알람 클릭 시 처리, 알람 읽음처리)
async function loadAlarms(userId) {
    let headerBellSection1 = document.getElementById("header_bell_section1");
    let headerBellSection2 = document.getElementById("header_bell_section2");

    try {
        const [alarmListResponse, alarmCountResponse] = await Promise.all([
            axios.get(`/api/alarm/alarms?userId=${userId}`),
            axios.get(`/api/alarm/alarms/countAll?userId=${userId}`)
        ]);

        const alarmList = alarmListResponse.data;
        const alarmCount = alarmCountResponse.data;

        headerBellSection1.innerHTML = `<strong>${alarmCount}개의 알림이 있습니다.</strong>`;

        headerBellSection2.innerHTML = "";
        if (alarmList.length === 0) {
            headerBellSection2.innerHTML = "<span>새로운 알림이 없습니다.</span>";
        } else {
            alarmList.forEach(alarm => {
                let alarmItem = document.createElement("span");
                alarmItem.innerText = alarm.alarmContent;
                alarmItem.style.display = "block";
                alarmItem.style.padding = "5px 0";
                alarmItem.style.cursor = "pointer";
                alarmItem.onclick = () => handleAlarmClick(alarm);
                headerBellSection2.appendChild(alarmItem);
            });
        }
    } catch (error) {
        console.error("알림 데이터를 불러오는 중 오류 발생:", error);
    }
}


function handleAlarmClick(alarm) {
    markAsRead(alarm.alarmId);

    if (alarm.relatedUrl && alarm.relatedUrl.trim() !== "") {
        setTimeout(() => {
            window.location.href = alarm.relatedUrl;
        }, 300);
    }
}

function markAsRead(alarmId) {
    axios.patch(`/api/alarm/alarms/read/${alarmId}`)
        .then(() => {
            console.log("알림 읽음 처리 완료");
            loadAlarms(userId);
        })
        .catch(error => console.error("읽음 처리 실패", error));
}

document.addEventListener("DOMContentLoaded", () => {

    document.getElementById("toggle-dark-mode").addEventListener("click", () => {
        document.documentElement.classList.toggle("dark-mode");
    });
})

document.addEventListener("DOMContentLoaded", function () {
    function getCookie(name) {
        const value = "; " + document.cookie;
        const parts = value.split("; " + name + "=");
        if (parts.length === 2) return parts.pop().split(";").shift();
    }

    const loginMessage = getCookie("loginMessage");
    const logoutMessage = getCookie("logoutMessage");

    if (loginMessage) {
        alert(loginMessage);
        document.cookie = "loginMessage=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    }

    if (logoutMessage) {
        alert(logoutMessage);
        document.cookie = "logoutMessage=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    }
});