document.addEventListener('DOMContentLoaded', function () {
    var calendarEl = document.getElementById('calendar');
    var calendar = new FullCalendar.Calendar(calendarEl, {
        initialView: 'dayGridMonth',
        headerToolbar:{
            left:'prev',
            center:'title',
            right:'next',

        },
        selectable: true,
        editable: true,  // 일정 드래그 및 크기 조절 가능
        locale: 'ko',  // 한국어 설정
        buttonText: {
            today: '오늘',
            month: '월',
            week: '주',
            day: '일',
            list: '목록'
        },
        // 서버에서 일정 불러오기
        events: function (fetchInfo, successCallback, failureCallback) {
            axios.get('/api/schedule/list')
                .then(response => {
                    console.log(response.data);
                    let events = response.data.map(event => ({
                        id: event.scheduleId,
                        title: event.title,
                        start: event.startDate,
                        end: event.endDate,
                        backgroundColor: event.color || '#3788d8',
                        extendedProps: { content: event.content }
                    }));
                    successCallback(events);
                })
                .catch(error => {
                    console.error('일정 불러오기 실패:', error);
                    failureCallback(error);
                });
        },

        // 일정 클릭 시 상세 모달 표시
        eventClick: function (info) {
            document.getElementById('scheduleTitle').value = info.event.title;
            document.getElementById('scheduleContent').value = info.event.extendedProps.content || '';
            document.getElementById('scheduleStart').value = formatDateToLocalISOString(info.event.start);
            document.getElementById('scheduleEnd').value = info.event.end ? formatDateToLocalISOString(info.event.end) : formatDateToLocalISOString(info.event.start);
            document.getElementById('scheduleColor').value = info.event.backgroundColor;
            document.getElementById('scheduleId').value = info.event.id;
            document.getElementById('deleteScheduleBtn').style.display = 'block';

            var scheduleModal = new bootstrap.Modal(document.getElementById('scheduleModal'));
            scheduleModal.show();
        },

        // 일정 드래그(이동) 시 자동 저장
        eventDrop: function (info) {
            let movedEvent = {
                scheduleId: info.event.id,
                title: info.event.title,
                content: info.event.extendedProps.content || '',
                startDate: formatDateToLocalISOString(info.event.start),
                endDate: info.event.end ? formatDateToLocalISOString(info.event.end) : formatDateToLocalISOString(info.event.start),
                color: info.event.backgroundColor
            };

            axios.put('/api/schedule/update', movedEvent)
                .then(response => {
                    if (response.data.success) {
                        alert('일정이 이동되었습니다!');
                    } else {
                        alert('일정 이동 실패!');
                    }
                })
                .catch(error => {
                    console.error('일정 이동 오류:', error);
                    alert('서버 오류 발생!');
                });
        },

        // 일정 크기 조절 시 자동 저장
        eventResize: function (info) {
            let updatedEvent = {
                scheduleId: info.event.id,
                title: info.event.title,
                content: info.event.extendedProps.content || '',
                startDate: formatDateToLocalISOString(info.event.start),
                endDate: formatDateToLocalISOString(info.event.end),
                color: info.event.backgroundColor
            };

            axios.put('/api/schedule/update', updatedEvent)
                .then(response => {
                    if (response.data.success) {
                        alert('일정이 수정되었습니다!');
                    } else {
                        alert('일정 수정 실패!');
                    }
                })
                .catch(error => {
                    console.error('일정 수정 오류:', error);
                    alert('서버 오류 발생!');
                });
        },

        // 날짜 클릭 시 일정 추가 모달 표시
        select: function (info) {
            document.getElementById('scheduleForm').reset();
            document.getElementById('scheduleStart').value = info.startStr;
            document.getElementById('scheduleEnd').value = info.endStr || info.startStr;
            document.getElementById('scheduleId').value = '';
            document.getElementById('deleteScheduleBtn').style.display = 'none';

            var scheduleModal = new bootstrap.Modal(document.getElementById('scheduleModal'));
            scheduleModal.show();
        }
    });

    calendar.render();

    // 일정 저장 처리
    document.getElementById('saveScheduleBtn').addEventListener('click', function () {
        let id = document.getElementById('scheduleId').value;
        let title = document.getElementById('scheduleTitle').value;
        let content = document.getElementById('scheduleContent').value;
        let start = document.getElementById('scheduleStart').value;
        let end = document.getElementById('scheduleEnd').value;
        let color = document.getElementById('scheduleColor').value;

        let scheduleData = {
            title: title,
            content: content,
            startDate: start,
            endDate: end,
            color: color
        };

        if (id) {
            scheduleData.scheduleId = id;
            axios.put('/api/schedule/update', scheduleData)
                .then(response => {
                    if (response.data.success) {
                        let event = calendar.getEventById(id);
                        event.setProp('title', title);
                        event.setExtendedProp('content', content);
                        event.setProp('backgroundColor', color);
                        alert('일정이 수정되었습니다!');
                    }
                });
        } else {
            axios.post('/api/schedule/save', scheduleData)
                .then(response => {
                    if (response.data.success) {
                        calendar.addEvent({
                            id: response.data.id,
                            title: title,
                            start: start,
                            end: end,
                            backgroundColor: color,
                            extendedProps: { content: content }
                        });
                        alert('일정이 추가되었습니다!');
                    }
                });
        }

        bootstrap.Modal.getInstance(document.getElementById('scheduleModal')).hide();
    });

    // 일정 삭제 처리
    document.getElementById('deleteScheduleBtn').addEventListener('click', function () {
        let id = document.getElementById('scheduleId').value;

        if (!id) {
            alert("일정 ID가 없습니다. 다시 시도해주세요!");
            return;
        }

        if (confirm("정말 삭제하시겠습니까?")) {
            axios.delete(`/api/schedule/delete/${id}`)
                .then(response => {
                    if (response.data.success) {
                        calendar.getEventById(id).remove();
                        alert('일정이 삭제되었습니다!');
                    } else {
                        alert('일정 삭제 실패!');
                    }
                })
                .catch(error => {
                    console.error('일정 삭제 오류:', error);
                    alert('서버 오류 발생!');
                });
        }

        bootstrap.Modal.getInstance(document.getElementById('scheduleModal')).hide();
    });

    // 날짜 포맷 조정 함수 (로컬 시간 처리)
    function formatDateToLocalISOString(date) {
        let localDate = new Date(date);
        localDate.setMinutes(localDate.getMinutes() - localDate.getTimezoneOffset());
        return localDate.toISOString().slice(0, 10);
    }
});

document.addEventListener('DOMContentLoaded', function () {
    const colorOptions = document.querySelectorAll('.color-option');
    const scheduleColorInput = document.getElementById('scheduleColor');

    colorOptions.forEach(option => {
        option.addEventListener('click', function () {
            const selectedColor = this.getAttribute('data-color');

            // 색상 변경
            scheduleColorInput.value = selectedColor;

            // 이전 선택된 색상 제거
            colorOptions.forEach(opt => opt.classList.remove('selected'));

            // 선택된 색상 강조
            this.classList.add('selected');
        });
    });
});
