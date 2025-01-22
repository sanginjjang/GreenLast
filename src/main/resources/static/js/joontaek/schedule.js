// document.addEventListener('DOMContentLoaded', function() {
//     var calendarEl = document.getElementById('calendar');
//     var calendar = new FullCalendar.Calendar(calendarEl, {
//         initialView: 'dayGridMonth',
//         selectable: true,
//         events: [
//             {
//                 title: '물주기',
//                 start: '2025-01-01'
//             },
//             {
//                 title: '뚜껑 닫기',
//                 start: '2025-01-03',
//                 end: '2025-01-05'
//             }
//         ],
//
//
//         select: function(info) {
//             let title = prompt('일정 제목을 입력하세요:');
//             if (title) {
//
//                 console.log("상준씨 왜이래요?");
//                 console.log(info);
//                 console.log("ㅋㅋ");
//                 let newEvent = {
//                     title: title,
//                     startDate: info.startStr,  // ISO 형식 "2025-01-01T00:00:00"
//                     endDate: info.endStr || info.startStr,
//                     content : "김상준의 스케쥴"// 끝 날짜가 없으면 시작 날짜로 설정
//                 };
//
//
//
//                 //-------------------------------
//                 //-------------------------------//-------------------------------
//                 //-------------------------------
//
//
//                 axios.post('/api/schedule/save', newEvent, {
//                     headers: {
//
//                         'Content-Type': 'application/json'
//                     }
//
//                 })
//
//                     .then(function(response) {
//                         if(response.data.success) {
//                             calendar.addEvent(newEvent);
//                             alert('일정이 추가되었습니다!');
//                         } else {
//                             alert('일정 추가 실패!');
//                         }
//                     })
//                     .catch(function(error) {
//                         console.error('서버 오류 발생:', error);
//                         alert('서버 오류 발생!');
//                     });
//             }
//
//             calendar.unselect();
//         }
//     });
//
//     calendar.render();
// });
//
document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',  // 기본 월별 보기
        selectable: true,             // 날짜 선택 가능

        // 서버에서 일정 가져오기 (페이지 로드 시 자동 실행)
        events: function(fetchInfo, successCallback, failureCallback) {
            axios.get('/api/schedule/list')
                .then(function(response) {
                    successCallback(response.data);
                })
                .catch(function(error) {
                    console.error('일정 불러오기 실패:', error);
                    failureCallback(error);
                });
        },

        // 일정 추가 기능
        select: function(info) {
            let title = prompt('일정 제목을 입력하세요:');
            let content = prompt('일정 내용을 입력하세요:');

            if (title && content) {
                console.log("일정 추가 정보:", info);

                let newEvent = {
                    title: title,
                    content: content,  // 일정 내용 추가
                    startDate: info.startStr,  // ISO 형식 "2025-01-01T00:00:00"
                    endDate: info.endStr || info.startStr  // 종료 날짜가 없으면 시작 날짜로 설정
                };

                // 서버로 일정 전송
                axios.post('/api/schedule/save', newEvent, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(function(response) {
                        if (response.data.success) {
                            newEvent.id = response.data.id;  // 서버에서 받은 ID 할당
                            calendar.addEvent(newEvent);  // 캘린더에 즉시 추가
                            alert('일정이 추가되었습니다!');
                        } else {
                            alert('일정 추가 실패!');
                        }
                    })
                    .catch(function(error) {
                        console.error('서버 오류 발생:', error);
                        alert('서버 오류 발생!');
                    });
            }

            calendar.unselect();  // 선택 해제
        },

        // 일정 클릭 시 삭제 기능
        eventClick: function(info) {
            if (confirm(`'${info.event.title}' 일정을 삭제하시겠습니까?`)) {
                axios.delete(`/api/schedule/delete/${info.event.id}`)
                    .then(response => {
                        if (response.data.success) {
                            info.event.remove();  // 캘린더에서 즉시 삭제
                            alert('일정이 삭제되었습니다!');
                        }
                    })
                    .catch(error => {
                        console.error('일정 삭제 오류:', error);
                        alert('일정 삭제 실패!');
                    });
            }
        }
    });

    calendar.render();
});
