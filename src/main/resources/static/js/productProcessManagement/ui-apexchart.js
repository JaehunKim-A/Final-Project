/*
    production Amount Line Chart
*/
var productionAmountLineChartOptions;
var productionAmountLineChart;

document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 데이터가 있으면 사용하고, 없으면 API에서 가져옴
    let productionAmounts = window.productionAmountsJson || [];
    let dayList = window.daysJson || [];

    try {
        if (typeof productionAmountsJson === 'string') {
            productionAmounts = JSON.parse(productionAmountsJson);
        }
        if (typeof daysJson === 'string') {
            dayList = JSON.parse(daysJson);
        }
    } catch (error) {
        console.error("JSON 파싱 오류:", error);
        // API로 데이터 가져오기
        fetchProductionData();
    }

    initializeProductionAmountChart(productionAmounts, dayList);
});

// 생산량 데이터 가져오기
function fetchProductionData() {
    fetch('/api/productProcessManagement/machineHistory/twoWeeks')
        .then(response => response.json())
        .then(data => {
            const productionAmounts = [];
            const groupedData = {};
            
            // 기계별로 데이터 그룹화
            data.dtoList.forEach(item => {
                groupedData[item.machineId] = item.productionAmountData;
            });
            
            // 차트 데이터 형식으로 변환
            for (const [key, value] of Object.entries(groupedData)) {
                productionAmounts.push({
                    name: key,
                    data: value
                });
            }
            
            initializeProductionAmountChart(productionAmounts, data.dayList);
        })
        .catch(error => {
            console.error("생산량 데이터 로드 실패:", error);
        });
}

// 생산량 차트 초기화
function initializeProductionAmountChart(productionAmounts, dayList) {

    const formattedDayList = dayList.map(timestamp => {
        // 타임스탬프가 문자열인지 숫자인지 확인하고 처리
        let date;
        if (typeof timestamp === 'string') {
            // 문자열이 숫자로만 이루어져 있는지 확인
            if (/^\d+$/.test(timestamp)) {
                date = new Date(Number(timestamp));
            } else {
                date = new Date(timestamp);
            }
        } else {
            date = new Date(timestamp);
        }

        // 유효한 날짜인지 확인
        if (isNaN(date.getTime())) {
            console.error('Invalid date from timestamp:', timestamp);
            return 'Invalid';
        }

        return date.toLocaleDateString('ko-KR', {
            month: '2-digit',
            day: '2-digit'
        });
    });

    productionAmountLineChartOptions = {
        chart: {
            type: "line",
            height: 250,
        },
        series: productionAmounts,
        xaxis: {
            categories: formattedDayList,
        },
    }

    if (document.querySelector("#production-amount-line-chart")) {
        productionAmountLineChart = new ApexCharts(document.querySelector("#production-amount-line-chart"), productionAmountLineChartOptions);
        productionAmountLineChart.render();
    }
}

// 생산량 차트 업데이트
function updateProductionAmountChart(productionAmounts, dayList) {
    const formattedDayList = dayList.map(timestamp => {
        const date = new Date(Number(timestamp));
        return date.toLocaleDateString('ko-KR', {
            month: '2-digit',
            day: '2-digit'
        });
    });

    if (productionAmountLineChart) {
        productionAmountLineChart.updateOptions({
            series: productionAmounts,
            xaxis: {
                categories: formattedDayList
            }
        });
    } else {
        initializeProductionAmountChart(productionAmounts, dayList);
    }
}

/*
    Machine Raw Material Reserve Bar Chart
*/
var machineRawMaterialReserveBarChartOptions;
var machineRawMaterialReserveBarChart;

document.addEventListener("DOMContentLoaded", function() {
    machineRawMaterialReserveBarChartOptions = {
        series: [
            {
                name: "Net Profit",
                data: [44, 55, 57, 56, 61, 58, 63, 60, 66],
            },
            {
                name: "Revenue",
                data: [76, 85, 101, 98, 87, 105, 91, 114, 94],
            },
            {
                name: "Free Cash Flow",
                data: [35, 41, 36, 26, 45, 48, 52, 53, 41],
            },
        ],
        chart: {
            type: "bar",
            height: 350,
        },
        plotOptions: {
            bar: {
                horizontal: false,
                columnWidth: "55%",
                endingShape: "rounded",
            },
        },
        dataLabels: {
            enabled: false,
        },
        stroke: {
            show: true,
            width: 2,
            colors: ["transparent"],
        },
        xaxis: {
            categories: ["Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct"],
        },
        yaxis: {
            title: {
                text: "$ (thousands)",
            },
        },
        fill: {
            opacity: 1,
        },
        tooltip: {
            y: {
                formatter: function (val) {
                    return "$ " + val + " thousands"
                },
            },
        },
    }

    if (document.querySelector("#machine-raw-material-reserve-bar-chart")) {
        machineRawMaterialReserveBarChart = new ApexCharts(document.querySelector("#machine-raw-material-reserve-bar-chart"), machineRawMaterialReserveBarChartOptions);
        machineRawMaterialReserveBarChart.render();
    }
});

/*
    Machine Raw Material Consume Scatter Chart
*/
var machineRawMaterialConsumeScatterChartOptions;
var machineRawMaterialConsumeScatterChart;

document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 데이터가 있으면 사용하고, 없으면 API에서 가져옴
    let rawMaterialConsume = [];
    
    try {
        if (typeof rawMaterialConsumeJson === 'string') {
            rawMaterialConsume = JSON.parse(rawMaterialConsumeJson);
        } else if (window.rawMaterialConsumeJson) {
            rawMaterialConsume = window.rawMaterialConsumeJson;
        } else {
            fetchRawMaterialConsumeData();
            return;
        }
        
        initializeRawMaterialConsumeChart(rawMaterialConsume);
    } catch (error) {
        console.error("JSON 파싱 오류:", error);
        // API로 데이터 가져오기
        fetchRawMaterialConsumeData();
    }
});

// 원자재 소비량 데이터 가져오기
function fetchRawMaterialConsumeData() {
    fetch('/api/productProcessManagement/rawMaterial/consume')
        .then(response => response.json())
        .then(data => {
            // 기계별 총 소비량 계산
            const machineConsume = {};
            data.forEach(item => {
                if (machineConsume[item.machineId]) {
                    machineConsume[item.machineId] += item.quantity;
                } else {
                    machineConsume[item.machineId] = item.quantity;
                }
            });
            
            // 산점도 데이터 형식으로 변환
            const scatterData = Object.entries(machineConsume).map(([key, value]) => ({
                x: key,
                y: value
            }));
            
            const seriesData = [{
                name: "Raw Material Consume",
                data: scatterData
            }];
            
            initializeRawMaterialConsumeChart(seriesData);
        })
        .catch(error => {
            console.error("원자재 소비량 데이터 로드 실패:", error);
        });
}

// 원자재 소비량 차트 초기화
function initializeRawMaterialConsumeChart(rawMaterialConsume) {
    machineRawMaterialConsumeScatterChartOptions = {
        chart: {
            type: 'scatter',
            height: 350
        },
        series: rawMaterialConsume,
        xaxis: {
            title: { text: "machine ID" },
            type: 'category'
        },
        yaxis: {
            title: { text: "raw material consume quantity" },
            type: 'numeric'
        }
    };

    if (document.querySelector("#machine-raw-material-consume-scatter-chart")) {
        machineRawMaterialConsumeScatterChart = new ApexCharts(document.querySelector("#machine-raw-material-consume-scatter-chart"), machineRawMaterialConsumeScatterChartOptions);
        machineRawMaterialConsumeScatterChart.render();
    }
}

// 원자재 소비량 차트 업데이트
function updateRawMaterialConsumeChart(rawMaterialConsume) {
    if (machineRawMaterialConsumeScatterChart) {
        machineRawMaterialConsumeScatterChart.updateSeries(rawMaterialConsume);
    } else {
        initializeRawMaterialConsumeChart(rawMaterialConsume);
    }
}

/*
    Machine Raw Material Reserve Heatmap Chart
*/
var machineRawMaterialReserveHeatmapChartOption;
var machineRawMaterialReserveHeatmapChart;

document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 데이터가 있으면 사용하고, 없으면 API에서 가져옴
    let seriesData = [];
    
    try {
        if (typeof rawMaterialReserveJson === 'string') {
            seriesData = JSON.parse(rawMaterialReserveJson);
        } else if (window.rawMaterialReserveJson) {
            seriesData = window.rawMaterialReserveJson;
        } else {
            fetchRawMaterialReserveData();
            return;
        }
        
        initializeRawMaterialReserveChart(seriesData);
    } catch (error) {
        console.error("JSON 파싱 오류:", error);
        // API로 데이터 가져오기
        fetchRawMaterialReserveData();
    }
});

// 원자재 재고량 데이터 가져오기
function fetchRawMaterialReserveData() {
    fetch('/api/productProcessManagement/rawMaterial/reserve')
        .then(response => response.json())
        .then(data => {
            // 원자재 코드별로 그룹화
            const groupedByMaterial = {};
            data.forEach(item => {
                if (!groupedByMaterial[item.materialCode]) {
                    groupedByMaterial[item.materialCode] = [];
                }
                
                groupedByMaterial[item.materialCode].push({
                    x: item.machineId,
                    y: item.stock
                });
            });
            
            // 차트 데이터 형식으로 변환
            const seriesData = Object.entries(groupedByMaterial).map(([key, value]) => ({
                name: key,
                data: value
            }));
            
            initializeRawMaterialReserveChart(seriesData);
        })
        .catch(error => {
            console.error("원자재 재고량 데이터 로드 실패:", error);
        });
}

// 원자재 재고량 차트 초기화
function initializeRawMaterialReserveChart(seriesData) {
    // 데이터 유효성 검사 추가
    if (!window.rawMaterialReserveJson || !Array.isArray(window.rawMaterialReserveJson)) {
        console.warn('원자재 재고량 데이터가 없거나 유효하지 않은 형식입니다.');
        window.rawMaterialReserveJson = []; // 빈 배열로 초기화
    }

    // 모든 기계 ID 추출
    const allMachineIds = new Set();
    seriesData.forEach(series => {
        series.data.forEach(point => {
            allMachineIds.add(point.x);
        });
    });

    // 모든 원자재 코드 추출
    const allMaterialCodes = seriesData.map(series => series.name);

    // 모든 기계 ID를 배열로 변환
    const machineIdCategories = Array.from(allMachineIds);

    // 데이터 보완: 빈 stock에 0 추가
    const completeSeriesData = allMaterialCodes.map(materialCode => {
        // 해당 원자재에 대한 기존 데이터 찾기
        const existingSeries = seriesData.find(series => series.name === materialCode);
        const existingData = existingSeries ? existingSeries.data : [];

        // 기존 데이터에서 기계 ID 추출
        const existingMachineIds = new Set(existingData.map(point => point.x));

        // 누락된 기계 ID에 대해 데이터 포인트 추가 (stock = 0)
        const missingDataPoints = [];
        machineIdCategories.forEach(machineId => {
            if (!existingMachineIds.has(machineId)) {
                missingDataPoints.push({
                    x: machineId,
                    y: 0  // 빈 stock에 0 할당
                });
            }
        });

        // 완전한 데이터 구성
        return {
            name: materialCode,
            data: [...existingData, ...missingDataPoints].sort((a, b) =>
                machineIdCategories.indexOf(a.x) - machineIdCategories.indexOf(b.x)
            )
        };
    });

    machineRawMaterialReserveHeatmapChartOption = {
        chart: {
            type: 'heatmap',
            height: 350
        },
        series: completeSeriesData, // 보완된 데이터 사용
        xaxis: {
            type: 'category',
            title: { text: "machine ID" },
            categories: machineIdCategories
        },
        yaxis: {
            title: { text: "raw material code" }
        },
        dataLabels: {
            enabled: true,
            formatter: function(val) {
                return val === 0 ? '' : val; // 0인 경우 빈 문자열 표시 (선택적)
            }
        },
        colors: ["#008FFB", "#00E396", "#FEB019"],
        plotOptions: {
            heatmap: {
                colorScale: {
                    ranges: [
                        { from: 0, to: 0, color: "#F5F5F5", name: "Empty" }, // 0에 대한 색상 설정
                        { from: 1, to: 30, color: "#00A100", name: "Low" },
                        { from: 31, to: 70, color: "#FFD700", name: "Medium" },
                        { from: 71, to: 100, color: "#FF0000", name: "High" }
                    ]
                }
            }
        }
    };

    if (document.querySelector("#machine-raw-material-reserve-heatmap-chart")) {
        machineRawMaterialReserveHeatmapChart = new ApexCharts(
            document.querySelector("#machine-raw-material-reserve-heatmap-chart"),
            machineRawMaterialReserveHeatmapChartOption
        );
        machineRawMaterialReserveHeatmapChart.render();
    }
}

// 원자재 재고량 차트 업데이트
function updateRawMaterialReserveChart(seriesData) {
    if (machineRawMaterialReserveHeatmapChart) {
        // 차트가 이미 있으면 데이터만 업데이트
        machineRawMaterialReserveHeatmapChart.destroy();
        initializeRawMaterialReserveChart(seriesData);
    } else {
        // 차트가 없으면 초기화
        initializeRawMaterialReserveChart(seriesData);
    }
}

/*
    Production Defective Pie Chart
*/
var productDefectivePieCharts = [];

document.addEventListener("DOMContentLoaded", function() {
    // 페이지 로드 시 데이터가 있으면 사용하고, 없으면 API에서 가져옴
    let productionDefectivePieChartDataList = [];
    
    try {
        if (typeof productionDefectiveAmountJson === 'string') {
            productionDefectivePieChartDataList = JSON.parse(productionDefectiveAmountJson);
        } else if (window.productionDefectiveAmountJson) {
            productionDefectivePieChartDataList = window.productionDefectiveAmountJson;
        } else {
            fetchDefectiveRatioData();
            return;
        }
        
        initializeDefectiveRatioChart(productionDefectivePieChartDataList);
    } catch (error) {
        console.error("JSON 파싱 오류:", error);
        // API로 데이터 가져오기
        fetchDefectiveRatioData();
    }
});

// 불량률 데이터 가져오기
function fetchDefectiveRatioData() {
    fetch('/api/productProcessManagement/machineHistory/today')
        .then(response => response.json())
        .then(data => {
            // 불량률 데이터 처리
            const defectiveData = data.map(history => {
                const defectiveRate = history.productionAmount > 0
                    ? (history.defectiveAmount / history.productionAmount * 100)
                    : 0.0;
                    
                return {
                    machineId: history.machineId,
                    series: [defectiveRate, 100 - defectiveRate],
                    labels: ["defective", "production"]
                };
            });
            
            initializeDefectiveRatioChart(defectiveData);
        })
        .catch(error => {
            console.error("불량률 데이터 로드 실패:", error);
        });
}

// 불량률 차트 초기화
function initializeDefectiveRatioChart(productionDefectivePieChartDataList) {
    const machineSelect = document.getElementById('machineSelect');
    if (!machineSelect) return;
    
    // 기존 옵션 제거
    machineSelect.innerHTML = '';
    
    const machineIds = productionDefectivePieChartDataList.map(data => data.machineId);

    machineIds.forEach((machineId, index)=> {
        const option = document.createElement('option');
        option.value = machineId;
        option.textContent = machineId;

        if (index === 0) {
            option.selected = true;
        }

        machineSelect.appendChild(option);
    });

    // 이전에 생성된 차트 정리
    productDefectivePieCharts.forEach(chart => {
        if (chart) {
            chart.destroy();
        }
    });
    productDefectivePieCharts = [];

    renderPieChart(machineIds[0], productionDefectivePieChartDataList);

    machineSelect.addEventListener('change', function() {
        renderPieChart(this.value || null, productionDefectivePieChartDataList);
    });
}

// 불량률 차트 렌더링
function renderPieChart(machineId = null, productionDefectivePieChartDataList) {
    const chartContainer = document.getElementById('product-defective-pie-chart');
    if (!chartContainer) return;
    
    chartContainer.innerHTML = '';

    const filteredData = machineId
        ? productionDefectivePieChartDataList.filter(data => data.machineId === machineId)
        : productionDefectivePieChartDataList;

    filteredData.forEach((machineData, index) => {
        const chartDiv = document.createElement('div');
        chartDiv.id = `chart-${machineData.machineId}`;
        chartContainer.appendChild(chartDiv);

        const productionDefectivePieChartOptions = {
            chart: {
                type: 'pie',
                height: 350
            },
            series: machineData.series,
            labels: machineData.labels,
            colors: ["#FF4560", "#00E396"],
            legend: {
                position: "bottom"
            },
            title: {
                text: `Machine ID: ${machineData.machineId}`
            },
            tooltip: {
                y: {
                    formatter: function (val) {
                        return val.toFixed(2) + "%";
                    }
                }
            }
        };
        const chart = new ApexCharts(chartDiv, productionDefectivePieChartOptions);
        chart.render();
        productDefectivePieCharts.push(chart);
    });
}

// 불량률 차트 업데이트
function updateDefectiveRatioChart(productionDefectivePieChartDataList) {
    // 현재 선택된 기계 ID 저장
    const machineSelect = document.getElementById('machineSelect');
    const currentSelectedMachineId = machineSelect ? machineSelect.value : null;
    
    // 차트 초기화 (기계 목록 포함)
    initializeDefectiveRatioChart(productionDefectivePieChartDataList);
    
    // 이전에 선택된 기계 ID가 있으면 해당 ID로 선택 복원
    if (currentSelectedMachineId && machineSelect) {
        const machineIds = productionDefectivePieChartDataList.map(data => data.machineId);
        if (machineIds.includes(currentSelectedMachineId)) {
            machineSelect.value = currentSelectedMachineId;
            renderPieChart(currentSelectedMachineId, productionDefectivePieChartDataList);
        }
    }
}

// 차트 모듈 초기화 함수 (productProcessManagement.js에서 호출)
function initializeCharts() {
    // 일단 차트에 필요한 데이터가 있는지 확인하고 초기화
    if (window.productionAmountsJson && window.daysJson) {
        updateProductionAmountChart(window.productionAmountsJson, window.daysJson);
    }
    
    if (window.rawMaterialConsumeJson) {
        updateRawMaterialConsumeChart(window.rawMaterialConsumeJson);
    }
    
    if (window.rawMaterialReserveJson) {
        updateRawMaterialReserveChart(window.rawMaterialReserveJson);
    }
    
    if (window.productionDefectiveAmountJson) {
        updateDefectiveRatioChart(window.productionDefectiveAmountJson);
    }
}

// 2주간 생산량 데이터 로드
async function loadProductionAmount2Week() {
    try {
        const response = await fetch('/api/productProcessManagement/machineHistory/twoWeeks');
        if (!response.ok) {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        const productionData = await response.json();

        // 데이터 변환
        const seriesData = [];
        const groupedData = {};

        productionData.dtoList.forEach(item => {
            groupedData[item.machineId] = item.productionAmountData;
        });

        for (const [key, value] of Object.entries(groupedData)) {
            seriesData.push({
                name: key,
                data: value
            });
        }

        // 전역 변수 업데이트
        window.productionAmountsJson = seriesData;
        window.daysJson = productionData.dayList;

        // 차트 업데이트
        if (typeof updateProductionAmountChart === 'function') {
            updateProductionAmountChart(seriesData, productionData.dayList);
        }

        return productionData;
    } catch (error) {
        console.error('2주간 생산량 데이터 로드 실패:', error);
        alert('생산량 데이터를 불러오는 중 오류가 발생했습니다.');
    }
}

// 오늘의 기계 이력 로드
async function loadMachineHistoryToday() {
    try {
        const response = await fetch('/api/productProcessManagement/machineHistory/today');
        if (!response.ok) {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        const todayHistoryData = await response.json();

        // 불량률 데이터 처리
        const defectiveData = todayHistoryData.map(history => {
            const defectiveRate = history.productionAmount > 0
                ? (history.defectiveAmount / history.productionAmount * 100)
                : 0.0;

            return {
                machineId: history.machineId,
                series: [defectiveRate, 100 - defectiveRate],
                labels: ["defective", "production"]
            };
        });

        window.productionDefectiveAmountJson = defectiveData;

        // 차트 업데이트
        if (typeof updateDefectiveRatioChart === 'function') {
            updateDefectiveRatioChart(defectiveData);
        }

        return todayHistoryData;
    } catch (error) {
        console.error('오늘의 기계 이력 로드 실패:', error);
        alert('오늘의 기계 이력을 불러오는 중 오류가 발생했습니다.');
    }
}

// 원자재 소비량 로드
async function loadRawMaterialConsume() {
    try {
        const response = await fetch('/api/productProcessManagement/rawMaterial/consume');
        if (!response.ok) {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        const consumeData = await response.json();

        // 기계별 총 소비량 계산
        const machineConsume = {};
        consumeData.forEach(item => {
            if (machineConsume[item.machineId]) {
                machineConsume[item.machineId] += item.quantity;
            } else {
                machineConsume[item.machineId] = item.quantity;
            }
        });

        // 산점도 데이터 형식으로 변환
        const scatterData = Object.entries(machineConsume).map(([key, value]) => ({
            x: key,
            y: value
        }));

        const seriesData = [{
            name: "Raw Material Consume",
            data: scatterData
        }];

        window.rawMaterialConsumeJson = seriesData;

        // 차트 업데이트
        if (typeof updateRawMaterialConsumeChart === 'function') {
            updateRawMaterialConsumeChart(seriesData);
        }

        return consumeData;
    } catch (error) {
        console.error('원자재 소비량 로드 실패:', error);
        alert('원자재 소비량 데이터를 불러오는 중 오류가 발생했습니다.');
    }
}

// 원자재 재고량 로드
async function loadRawMaterialReserve() {
    try {
        const response = await fetch('/api/productProcessManagement/rawMaterial/reserve');
        if (!response.ok) {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        const reserveData = await response.json();

        // 원자재 코드별로 그룹화
        const groupedByMaterial = {};
        reserveData.forEach(item => {
            if (!groupedByMaterial[item.materialCode]) {
                groupedByMaterial[item.materialCode] = [];
            }

            groupedByMaterial[item.materialCode].push({
                x: item.machineId,
                y: item.stock
            });
        });

        // 차트 데이터 형식으로 변환
        const seriesData = Object.entries(groupedByMaterial).map(([key, value]) => ({
            name: key,
            data: value
        }));

        window.rawMaterialReserveJson = seriesData;

        // 차트 업데이트
        if (typeof updateRawMaterialReserveChart === 'function') {
            updateRawMaterialReserveChart(seriesData);
        }

        return reserveData;
    } catch (error) {
        console.error('원자재 재고량 로드 실패:', error);
        alert('원자재 재고량 데이터를 불러오는 중 오류가 발생했습니다.');
    }
}