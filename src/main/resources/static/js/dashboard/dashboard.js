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