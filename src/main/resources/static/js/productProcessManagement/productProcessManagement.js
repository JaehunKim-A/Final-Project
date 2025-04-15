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

        // 데이터 전역 변수로 설정
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

        // 원자재 재고량 데이터
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

        if (typeof initializeCharts === 'function') {
            // 약간의 지연 후 차트 초기화
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

// Machine History 테이블 불러오기 함수
async function getMachineHistory({ page = 1, size = 10, sorter = 'historyId', isAsc = false, types = [], keyword = '' }) {
    const payload = {page, size, sorter, isAsc, types, keyword};

    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

    const result = await axios.post('/api/productProcessManagement/productProcessManagementPost', payload, {
        headers: {
            [header]: token
        }
    });
    return result.data;
}

document.addEventListener('DOMContentLoaded', function() {
    // 초기 상태 설정
    const state = {
        page: 1,
        size: 10,
        sorter: 'historyId',
        isAsc: false,
        type: '',
        keyword: ''
    };

    // 처음 로드 시 데이터 가져오기
    loadMachineHistoryData(state);

    // 검색 버튼 이벤트
    document.getElementById('machine-history-search-btn').addEventListener('click', function() {
        state.type = document.getElementById('machine-history-search-type').value;
        state.keyword = document.getElementById('machine-history-search-keyword').value;
        state.page = 1; // 검색 시 첫 페이지로 이동
        loadMachineHistoryData(state);
    });

    // 정렬 링크 이벤트 설정
    document.querySelectorAll('.dataTable-sorter').forEach(sorter => {
        sorter.addEventListener('click', function(e) {
            e.preventDefault();

            const sortField = this.getAttribute('data-sort');

            console.log(state.sorter);
            console.log(sortField);

            if (state.sorter === sortField) {
                if(!state.isAsc) {
                    state.isAsc = true;
                }
                else {
                    state.isAsc = false;
                    state.sorter = "historyId";
                }
            } else {
                state.sorter = sortField;
                state.isAsc = false; // 새 필드로 정렬 시 기본 내림차순
            }

            document.querySelectorAll('.dataTable-sorter').forEach(el => {
                el.classList.remove('asc', 'desc');
            });

            if (state.sorter !== 'historyId') {
                this.classList.add(state.isAsc ? 'asc' : 'desc');
            }

            loadMachineHistoryData(state);
        });
    });

    // 페이지 크기 변경 이벤트
    document.getElementById('machine-history-page-size').addEventListener('change', function() {
        state.size = parseInt(this.value);
        state.page = 1; // 페이지 크기 변경 시 첫 페이지로 이동
        loadMachineHistoryData(state);
    });

});

// Machine History 데이터를 불러오는 함수
async function loadMachineHistoryData(state) {
    try {
        // 로딩 상태 표시
        document.getElementById('machine-history-table-body').innerHTML = '<tr><td colspan="6" class="text-center">Loading...</td></tr>';

        // API 호출을 위한 파라미터 구성
        const payload = {
            page: state.page,
            size: state.size,
            sorter: state.sorter,
            isAsc: state.isAsc,
            // 배열이 아닌 문자열로 변환
            type: Array.isArray(state.type) ? state.type.join('') : state.type,
            keyword: state.keyword
        };

        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        // API 호출
        const response = await axios.post('/api/productProcessManagement/productProcessManagementPost', payload,{
            headers: {
                [header]: token
            }
        });
        const data = response.data;

        // 테이블 데이터 렌더링
        renderTableData(data);

        // 페이지네이션 렌더링
        renderPagination(data, state);

        // 현재 정렬 상태 표시
        updateSortIndicators(state);

    } catch (error) {
        console.error('Error loading machine history data:', error);
        console.error('Error details:', error.response?.data); // 추가 오류 정보 출력
        document.getElementById('machine-history-table-body').innerHTML =
            '<tr><td colspan="6" class="text-center text-danger">Error loading data. Please try again.</td></tr>';
    }
}

// 정렬 상태 표시기 업데이트
function updateSortIndicators(state) {
    document.querySelectorAll('.dataTable-sorter').forEach(sorter => {
        sorter.classList.remove('asc', 'desc');

        if (sorter.getAttribute('data-sort') === state.sorter) {
            sorter.classList.add(state.isAsc ? 'asc' : 'desc');
        }
    });
}

// 테이블 데이터 렌더링 함수
function renderTableData(data) {
    const tableBody = document.getElementById('machine-history-table-body');

    if (!data.dtoList || data.dtoList.length === 0) {
        tableBody.innerHTML = '<tr><td colspan="6" class="text-center">No data available</td></tr>';
        return;
    }

    let html = '';
    data.dtoList.forEach(history => {
        html += `<tr>
            <td>${history.historyId}</td>
            <td>${history.machineId}</td>
            <td>${history.productionAmount}</td>
            <td>${history.defectiveAmount}</td>
            <td>${history.productionDate || 'N/A'}</td>
            <td>${history.productionDateUpdate || 'N/A'}</td>
        </tr>`;
    });

    tableBody.innerHTML = html;
}

// 페이지네이션 렌더링 함수
function renderPagination(data, state) {
    const pagination = document.getElementById('machine-history-pagination');

    console.log('Pagination data:', data); // 데이터 구조 확인

    // total 필드 사용
    const totalItems = data.total || 0;
    const totalPages = Math.ceil(totalItems / state.size);
    const currentPage = state.page;

    console.log(`Total items: ${totalItems}, Total pages: ${totalPages}, Current page: ${currentPage}`);

    // 페이지 범위 계산
    let startPage = Math.max(1, currentPage - 2);
    let endPage = Math.min(totalPages, startPage + 4);

    if (endPage - startPage < 4 && totalPages > 5) {
        startPage = Math.max(1, endPage - 4);
    }

    let html = '';

    // 이전 버튼
    html += `<li class="page-item ${currentPage <= 1 ? 'disabled' : ''}">
        <a class="page-link" href="#" data-page="${currentPage - 1}">
            <span aria-hidden="true"><i class="bi bi-chevron-left"></i></span>
        </a>
    </li>`;

    // 페이지 번호
    for (let i = startPage; i <= endPage; i++) {
        html += `<li class="page-item ${i === currentPage ? 'active' : ''}">
            <a class="page-link" href="#" data-page="${i}">${i}</a>
        </li>`;
    }

    // 다음 버튼
    html += `<li class="page-item ${currentPage >= totalPages || totalPages === 0 ? 'disabled' : ''}">
        <a class="page-link" href="#" data-page="${currentPage + 1}">
            <span aria-hidden="true"><i class="bi bi-chevron-right"></i></span>
        </a>
    </li>`;

    pagination.innerHTML = html;

    // 페이지 클릭 이벤트 추가
    pagination.querySelectorAll('.page-link').forEach(link => {
        link.addEventListener('click', function(e) {
            e.preventDefault();

            // disabled 클래스가 있는 경우 클릭 무시
            if (this.parentElement.classList.contains('disabled')) {
                return;
            }

            const pageNum = parseInt(this.getAttribute('data-page'));
            console.log('Clicked page:', pageNum);

            // 유효한 페이지 번호 확인
            if (!isNaN(pageNum) && pageNum > 0 && pageNum <= totalPages && pageNum !== currentPage) {
                state.page = pageNum;
                loadMachineHistoryData(state);
            }
        });
    });
}