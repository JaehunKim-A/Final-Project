window.productionAmountsJson = [];
window.daysJson = [];
window.rawMaterialConsumeJson = [];
window.rawMaterialReserveJson = [];
window.productionDefectiveAmountJson = [];
window.machineGuiInfoJson = [];

document.addEventListener("DOMContentLoaded", function() {
    // 초기 데이터 로드
    loadDashboardData();
});

document.addEventListener("DOMContentLoaded", function() {
    // 테이블 초기화
    const tableBody = document.querySelector("#table1");
    const myDataTable = new simpleDatatables.DataTable(tableBody, {
        perPage: 10,
        searchable: true,
        sortable: true,
        labels: {
            placeholder: "고객을 검색하세요...",
            perPage: "{select} 행을 한 페이지에 표시",
            noRows: "고객을 찾을 수 없습니다.",
            info: "전체 {rows}개의 고객 중 {start}에서 {end}까지 표시",
        },
    });

    // 연도 선택기 설정
    const machineHistoryYearSelectInput = document.querySelector("#machine-history-year-select");
    let startYear = 2020;
    const currentYear = new Date().getFullYear();

    for(let year = startYear; year <= currentYear; year++) {
        let option = document.createElement("option");
        option.value = year;
        option.textContent = year;

        if (year === currentYear) {
            option.selected = true;  // 현재 연도일 경우 selected 속성 추가
        }

        machineHistoryYearSelectInput.appendChild(option);
    }

    // URL에서 year 파라미터 값을 가져와서 연도 선택기에 반영
    const urlParams = new URLSearchParams(window.location.search);
    const yearParam = urlParams.get('year');
    if (yearParam) {
        machineHistoryYearSelectInput.value = yearParam;
    }

    // 검색 버튼 이벤트 추가
    document.getElementById("machine-history-year-select-submit-btn").addEventListener("click", function() {
        const year = document.getElementById("machine-history-year-select").value;
        window.location.href = `/productProcessManagement/productProcessManagement?year=${year}`;
    });
});

document.querySelectorAll(".card-collapse-btn").forEach(button => {
    button.addEventListener("click", function() {
        this.closest(".card").classList.toggle("collapsed-card");
    });
});

// 대시보드 데이터 로드
async function loadDashboardData() {
    try {
        const response = await fetch('/api/productProcessManagement/dashboard');
        if (!response.ok) {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        const dashboardData = await response.json();

        // 데이터 전역 변수로 설정 (기존 스크립트와 호환성 유지)
        window.machineGuiInfoJson = dashboardData.machineGuiInfo || [];

        // 생산량 차트 데이터
        const seriesData = [];
        if (dashboardData.productionAmounts) {
            for (const [key, value] of Object.entries(dashboardData.productionAmounts)) {
                seriesData.push({
                    name: key,
                    data: value
                });
            }
        }
        window.productionAmountsJson = seriesData;
        window.daysJson = dashboardData.dayList || [];

        // 원자재 소비량 데이터
        let scatterData = [];
        if (dashboardData.rawMaterialConsume) {
            scatterData = Object.entries(dashboardData.rawMaterialConsume).map(([key, value]) => ({
                x: key,
                y: value
            }));
        }

        window.rawMaterialConsumeJson = [{
            name: "Raw Material Consume",
            data: scatterData
        }];

        // 원자재 재고량 데이터 - 형식 변환 추가
        if (dashboardData.rawMaterialReserve && Array.isArray(dashboardData.rawMaterialReserve)) {
            // 원자재 코드별로 그룹화
            const groupedByMaterial = {};
            dashboardData.rawMaterialReserve.forEach(item => {
                if (!groupedByMaterial[item.materialCode]) {
                    groupedByMaterial[item.materialCode] = [];
                }

                groupedByMaterial[item.materialCode].push({
                    x: item.machineId,
                    y: item.stock
                });
            });

            // 차트 데이터 형식으로 변환
            const reserveSeriesData = Object.entries(groupedByMaterial).map(([key, value]) => ({
                name: key,
                data: value
            }));

            window.rawMaterialReserveJson = reserveSeriesData;
        } else {
            window.rawMaterialReserveJson = []; // 데이터가 없을 경우 빈 배열 설정
        }

        // 불량률 데이터
        window.productionDefectiveAmountJson = dashboardData.productionDefectiveData || [];

        // 차트 초기화 전에 데이터 존재 여부 확인
        console.log("Chart data loaded:", {
            productionAmounts: window.productionAmountsJson,
            days: window.daysJson,
            consume: window.rawMaterialConsumeJson,
            reserve: window.rawMaterialReserveJson,
            defective: window.productionDefectiveAmountJson
        });

        if (typeof initializeCharts === 'function') {
            // 약간의 지연 후 차트 초기화 (데이터 설정 보장)
            setTimeout(() => {
                initializeCharts();
            }, 100);
        }

        // 기계 GUI 초기화
        if (typeof initializeFactoryGUI === 'function') {
            initializeFactoryGUI();
        }

        // 현재 탭에 따라 추가 데이터 로드
        const activePage = document.querySelector('.tab-content.active');
        if (activePage) {
            const pageId = activePage.id;
        }

        return dashboardData;
    } catch (error) {
        console.error('대시보드 데이터 로드 실패:', error);
        alert('데이터를 불러오는 중 오류가 발생했습니다.');
    }
}

// 탭 전환 함수
function openTab(pageId, button) {
    // 모든 탭 숨기기
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });

    // 선택한 탭 보이기
    document.getElementById(pageId).classList.add('active');

    // 모든 버튼 비활성화
    document.querySelectorAll('.tab-button').forEach(btn => {
        btn.classList.remove('active');
    });

    // 클릭한 버튼 활성화
    button.classList.add('active');

    // 탭 전환 시 데이터 로드
    if (pageId === 'page2') {
        // 차트 데이터 로드
        loadProductionAmount2Week();
        loadRawMaterialConsume();
        loadRawMaterialReserve();
        loadMachineHistoryToday();
    } else if (pageId === 'page1') {
        // 기계 GUI 정보 로드
        loadMachineGuiInfo();
    }
}