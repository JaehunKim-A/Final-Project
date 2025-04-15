document.addEventListener('DOMContentLoaded', function() {
  const calendarElement = document.querySelector('.flatpickr-calendar');

  if (calendarElement) {
    const calendar = flatpickr(calendarElement, {
      // 항상 열려있는 인라인 모드로 설정
      inline: true,

      // 현재 날짜 기본값으로 설정
      defaultDate: 'today',

      // 날짜 선택 후 이벤트
      onChange: function(selectedDates, dateStr, instance) {
        console.log('선택된 날짜:', dateStr);
        // 선택된 날짜에 대한 추가 작업을 여기에 구현
      },

      // 추가 옵션
      locale: 'ko', // 한국어 로케일 사용 (flatpickr/dist/l10n/ko.js 필요)
      weekNumbers: true, // 주차 표시
      showMonths: 1, // 표시할 월 개수

      // 사용 가능한 날짜 범위 설정 (필요한 경우)
      // minDate: "2023-01-01",
      // maxDate: "2025-12-31"
    });

    // 캘린더가 생성된 후 DOM 요소에 추가 CSS 클래스 적용
    setTimeout(() => {
      const calendarContainer = document.querySelector('.flatpickr-calendar.inline');
      if (calendarContainer) {
        calendarContainer.classList.add('full-width-calendar');
      }
    }, 100);
  }
});

// ----------- Notice -------------
document.addEventListener('DOMContentLoaded', function() {
    // 현재 날짜 및 요일 가져오기
    const now = new Date();
    const days = ['일', '월', '화', '수', '목', '금', '토'];
    const dayOfWeek = days[now.getDay()];

    // 날짜 포맷팅
    const year = now.getFullYear();
    const month = now.getMonth() + 1;
    const date = now.getDate();
    const formattedDate = `${year}년 ${month}월 ${date}일 (${dayOfWeek})`;

    // 현재 날짜 표시
    document.getElementById('currentDate').textContent = formattedDate;

    // 요일별 메시지 설정
    let greeting, message;

    switch(now.getDay()) {
        case 0: // 일요일
            greeting = "즐거운 주말입니다";
            message = "휴식을 취하며 다음 주를 준비하세요. 내일은 새로운 한 주가 시작됩니다!";
            break;
        case 1: // 월요일
            greeting = "활기찬 한 주의 시작";
            message = "이번 주 목표를 설정하고 긍정적인 마음으로 시작하세요!";
            break;
        case 2: // 화요일
            greeting = "화이팅하는 화요일";
            message = "어제의 성과를 이어가며 오늘도 생산적인 하루 보내세요.";
            break;
        case 3: // 수요일
            greeting = "수요일입니다";
            message = "한 주의 중간을 지나고 있습니다. 잠시 숨을 고르고 남은 일정을 확인하세요.";
            break;
        case 4: // 목요일
            greeting = "목표를 향해 나아가는 목요일";
            message = "주말이 다가오고 있습니다. 이번 주 목표 달성을 위한 마지막 노력을 기울여보세요.";
            break;
        case 5: // 금요일
            greeting = "활기찬 금요일";
            message = "한 주의 마무리를 잘 하고, 주간 보고서 제출 잊지 마세요. 행복한 주말 되세요!";
            break;
        case 6: // 토요일
            greeting = "즐거운 주말입니다";
            message = "한 주 동안 고생 많으셨습니다. 즐거운 주말 보내세요!";
            break;
    }

    // 메시지 업데이트
    document.getElementById('dailyGreeting').textContent = greeting;
    document.getElementById('dailyMessage').textContent = message;
});

// ---------- schedule -----------
document.addEventListener('DOMContentLoaded', function () {
  const dayOfWeek = new Date().getDay(); // 0 = 일요일, ..., 6 = 토요일
  const row1 = document.querySelector('.row-1');
  const row2 = document.querySelector('.row-2');

  const scheduleByDay = {
    1: [
      { task: "📝 주간 회의", time: "09:00 AM" },
      { task: "📦 프로젝트 킥오프", time: "11:00 AM" },
      { task: "📧 메일 정리", time: "04:00 PM" }
    ],
    2: [
      { task: "📊 데이터 분석 미팅", time: "10:00 AM" },
      { task: "🧪 기능 테스트", time: "01:00 PM" },
      { task: "🧾 예산 검토", time: "03:00 PM" }
    ],
    3: [
      { task: "📋 중간 리뷰", time: "09:30 AM" },
      { task: "🧠 UX 회의", time: "02:00 PM" },
      { task: "📤 외부 보고서 제출", time: "05:00 PM" }
    ],
    4: [
      { task: "🚀 기능 배포", time: "10:00 AM" },
      { task: "📱 앱 테스트", time: "01:30 PM" },
      { task: "👨‍👩‍👧 팀 체크인", time: "04:00 PM" }
    ],
    5: [
      { task: "✅ 주간 정리 미팅", time: "09:00 AM" },
      { task: "🧾 리포트 제출", time: "11:30 AM" },
      { task: "🍺 팀 회식 준비", time: "05:00 PM" }
    ],
    6: [
      { task: "🛒 장보기", time: "10:00 AM" },
      { task: "🧹 집 청소", time: "02:00 PM" },
      { task: "🎮 게임 타임", time: "07:00 PM" }
    ],
    0: [
      { task: "😴 늦잠 자기", time: "아무때나" },
      { task: "📚 책 읽기", time: "오후" },
      { task: "🍕 맛있는 거 먹기", time: "저녁" }
    ]
  };

  const dayNames = ["일요일", "월요일", "화요일", "수요일", "목요일", "금요일", "토요일"];
  const todaySchedule = scheduleByDay[dayOfWeek] || [];

  // 오늘 일정 출력
  row1.innerHTML = `
    <div class="col-md-12">
      <h5>📆 오늘의 일정</h5>
      <ul class="list-group">
        ${todaySchedule.map(item => `
          <li class="list-group-item d-flex justify-content-between align-items-center">
            ${item.task}
            <span class="badge bg-info">${item.time}</span>
          </li>
        `).join('')}
      </ul>
    </div>
  `;

  // 주간 일정 버튼 + 주간일정 담을 영역 추가
  row2.innerHTML = `
    <div class="col-md-12 mb-3">
      <button id="show-weekly-btn" class="btn btn-outline-primary w-100">📅 주간 일정 보기</button>
    </div>
    <div class="col-md-12 weekly-schedule" style="display: none;"></div>
  `;

  // 버튼 클릭 시 주간 일정 출력
  const showWeeklyBtn = document.getElementById('show-weekly-btn');
  const weeklyScheduleDiv = document.querySelector('.weekly-schedule');

  showWeeklyBtn.addEventListener('click', function () {
    // 이미 열려있으면 닫기 (토글 기능)
    if (weeklyScheduleDiv.style.display === 'block') {
      weeklyScheduleDiv.style.display = 'none';
      showWeeklyBtn.textContent = '📅 주간 일정 보기';
      return;
    }

    // 주간 일정 생성
    let weeklyHTML = '<h5>📖 주간 전체 일정</h5>';
    for (let i = 0; i < 7; i++) {
      const daySchedule = scheduleByDay[i] || [];
      weeklyHTML += `
        <div class="mb-3">
          <h6 class="text-primary">${dayNames[i]}</h6>
          <ul class="list-group">
            ${daySchedule.map(item => `
              <li class="list-group-item d-flex justify-content-between align-items-center">
                ${item.task}
                <span class="badge bg-secondary">${item.time}</span>
              </li>
            `).join('')}
          </ul>
        </div>
      `;
    }

    weeklyScheduleDiv.innerHTML = weeklyHTML;
    weeklyScheduleDiv.style.display = 'block';
    showWeeklyBtn.textContent = '❌ 주간 일정 닫기';
  });
});