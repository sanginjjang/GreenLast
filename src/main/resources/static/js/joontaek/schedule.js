document.addEventListener('DOMContentLoaded', function() {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        selectable: true,  // 사용자가 날짜 선택 가능
        events: [
            {
                title: '물주기',
                start: '2025-01-01'
            },
            {
                title: '뚜껑 닫기',
                start: '2025-01-03',
                end: '2025-01-05'
            }
        ],
        select: function(info) {
            let title = prompt('일정 제목을 입력하세요:');
            let description = prompt('일정 내용을 입력하세요:');

            if (title && description) {
                let newEvent = {
                    title: title,
                    description: description,
                    start: info.startStr,  // 선택한 시작 날짜 (ISO 형식)
                    end: info.endStr || info.startStr,  // 종료 날짜가 없으면 시작일로 설정
                    allDay: info.allDay
                };

                // 서버로 일정 전송
                axios.post('/api/calendar/save', newEvent, {
                    headers: {
                        'Content-Type': 'application/json'
                    }
                })
                    .then(function(response) {
                        if(response.data.success) {
                            calendar.addEvent({
                                id: response.data.id,  // 서버에서 받은 ID
                                title: newEvent.title,
                                description: newEvent.description,
                                start: newEvent.start,
                                end: newEvent.end,
                                allDay: newEvent.allDay
                            });
                            alert('일정이 추가되었습니다!');
                        } else {
                            alert('일정 추가에 실패했습니다.');
                        }
                    })
                    .catch(function(error) {
                        console.error('일정 추가 실패:', error);
                        alert('서버 오류 발생!');
                    });
            }

            calendar.unselect();  // 선택 해제
        },
        eventClick: function(info) {
            if (confirm("'" + info.event.title + "' 일정을 삭제하시겠습니까?")) {
                axios.delete('/api/calendar/delete/' + info.event.id)
                    .then(response => {
                        if (response.data.success) {
                            info.event.remove();
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
