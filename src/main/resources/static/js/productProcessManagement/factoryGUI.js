/**
 * 공장 GUI 렌더링 스크립트 (factoryGUI.js)
 * 공장 GUI 시각화 파트
 */

// 전역 변수 선언
let canvas, ctx;
let canvasPadding = 40;
// x, y축 타일 개수
let tileXNum = 3;
let tileYNum = 5;

let drawableWidth, drawableHeight;
let xCoordinateHorizontal, xCoordinateVertical, yCoordinateHorizontal, yCoordinateVertical;
let tileXSize, tileYSize, tileXZeroPoint, tileYZeroPoint;
let imagePositions = [];

let hoveredMachineIndex = null;
let selectedMachineIndex = null; // 클릭으로 선택된 머신 인덱스

let gridTiles = [];
let hoveredTileIndex = null;

let lastMouseX = 0;
let lastMouseY = 0;

let loadedImages = {};
let factoryData = [];
let lineSelector;

// 이미지 경로 정의
const blockImages = {
    "assembly_machine": "/img/productProcessManagement/assembly_machine.png",
    "combination_machine": "/img/productProcessManagement/combination_machine.png",
};

/**
 * 공장 GUI 초기화 함수 - 메인 스크립트에서 호출
 */
function initializeFactoryGUI() {
    canvas = document.getElementById("factoryCanvas");
    if (!canvas) {
        console.error("Factory canvas element not found!");
        return;
    }

    ctx = canvas.getContext("2d");

    // 캔버스 크기 설정
    canvas.width = canvas.parentElement.offsetWidth * 0.9;
    canvas.height = (canvas.width * 2) / 3;

    // 계산값 초기화
    drawableWidth = canvas.width - (canvasPadding * 2);
    drawableHeight = canvas.height - (canvasPadding * 2);

    xCoordinateHorizontal = (drawableWidth / 3) / tileXNum;
    xCoordinateVertical = (drawableHeight / 3) / tileXNum;
    yCoordinateHorizontal = ((drawableWidth * 2) / 3) / tileYNum;
    yCoordinateVertical = ((drawableHeight * 2) / 3) / tileYNum;

    tileXSize = xCoordinateVertical * 2;
    tileYSize = yCoordinateHorizontal * 2;
    tileXZeroPoint = canvasPadding + (drawableWidth / 3) + (yCoordinateHorizontal / 2);
    tileYZeroPoint = canvasPadding + xCoordinateVertical / 2;

    // Line 선택기 초기화
    lineSelector = document.getElementById("lineSelector");
    if (lineSelector) {
        lineSelector.addEventListener("change", function () {
            const selectedLine = lineSelector.value;
            const filteredData = selectedLine === ""
                ? []
                : factoryData.filter(machine => machine.line === selectedLine);

            // 기계 이미지 렌더링
            renderAll(filteredData);

            // 차트도 같은 라인으로 업데이트
            renderProductionTargetChart(selectedLine);
        });

        if (lineSelector.options.length > 0) {
            const initialSelectedLine = lineSelector.value;
            const filteredData = initialSelectedLine === ""
                ? []
                : factoryData.filter(machine => machine.line === initialSelectedLine);

            // 초기 데이터로 렌더링
            renderAll(filteredData);

            // 초기 선택된 라인으로 차트 렌더링
            renderProductionTargetChart(initialSelectedLine);
        }
    } else {
        console.warn("Line selector element not found!");
    }

    // 마우스 이벤트 리스너 설정
    canvas.addEventListener("mousemove", handleMouseMove);

    // 클릭 이벤트 리스너 추가
    canvas.addEventListener("click", handleCanvasClick);

    // 캔버스 외부 클릭 감지를 위한 문서 전체 클릭 이벤트 추가
    document.addEventListener("click", handleDocumentClick);

    // JSON 데이터 처리 및 초기 렌더링
    processMachineGuiInfo(window.machineGuiInfoJson);
}

/**
 * 기계 GUI 정보 업데이트 함수 - 메인 스크립트에서 호출됨
 */
function updateFactoryGUI(machineGuiInfoJson) {
    processMachineGuiInfo(machineGuiInfoJson);
}

/**
 * 기계 GUI 정보 처리
 */
function processMachineGuiInfo(machineGuiInfoList) {
    if (!machineGuiInfoList || !Array.isArray(machineGuiInfoList)) {
        console.error("Invalid machine GUI info:", machineGuiInfoList);
        machineGuiInfoList = [];
    }

    // machine ID 기준으로 데이터 그룹화
    const groupedMachines = {};
    machineGuiInfoList.forEach(item => {
        if (!groupedMachines[item.machineId]) {
            groupedMachines[item.machineId] = {
                machineId: item.machineId,
                xCoordinate: Number(item.xcoordinate),
                yCoordinate: Number(item.ycoordinate),
                machineType: item.machineType || "assembly_machine",
                line: item.machineId.split('_')[0], // machine_id에서 line 추출
                materials: []
            };
        }

        // machine에 material 정보 추가
        if (item.materialCode) {
            groupedMachines[item.machineId].materials.push({
                materialCode: item.materialCode,
                stock: item.stock
            });
        }
    });

    // 그룹화된 데이터를 factoryData 형식으로 변환
    factoryData = Object.values(groupedMachines).map(machine => ({
        x: machine.xCoordinate,
        y: machine.yCoordinate,
        type: machine.machineType,
        machine_id: machine.machineId,
        line: machine.line,
        materials: machine.materials
    }));

    updateLineSelector();

    renderFactory();
}

/**
 * Line 선택기 옵션 업데이트
 */
function updateLineSelector() {
    if (!lineSelector) return;

    // 기존 옵션 제거 (전체 옵션은 유지)
    while (lineSelector.options.length > 0) {
        lineSelector.remove(0);
    }

    // 라인 목록 추출 및 중복 제거
    const lines = new Set(factoryData.map(machine => machine.line));

    // Dropdown에 옵션 추가
    lines.forEach(line => {
        if (!line) return; // 라인 정보가 없는 경우 건너뛰기

        const option = document.createElement("option");
        option.value = line;
        option.textContent = `Line ${line}`;
        lineSelector.appendChild(option);
    });

    // 라인 옵션이 추가된 후 첫 번째 값으로 초기 렌더링
    if (lineSelector.options.length > 0) {
         const selectedLine = lineSelector.value;
         // 초기 선택된 라인으로 필터링된 데이터
         const filteredData = factoryData.filter(machine => machine.line === selectedLine);

         // 초기 데이터로 렌더링
         renderAll(filteredData);

         // 초기 선택된 라인으로 차트 렌더링 (데이터가 로드된 후)
         if (productionTargetRatioData.length > 0) {
             renderProductionTargetChart(selectedLine);
         }
     }
}

/**
 * 이미지 로딩 함수
 */
function loadImage(src) {
    return new Promise((resolve, reject) => {
        const img = new Image();
        img.src = src;

        img.onload = () => {
            resolve(img);
        };

        img.onerror = (err) => {
            console.error("이미지 로드 실패:", src, err);
            // 에러 발생 시에도 기본 이미지나 대체 이미지를 제공
            const fallbackImg = new Image();
            fallbackImg.width = 50;
            fallbackImg.height = 50;
            resolve(fallbackImg);
        };
    });
}

/**
 * 그리드 타일 그리기 함수
 */
function drawGrid() {
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    gridTiles.length = 0;

    for (let x = 0; x < tileXNum; x++) {
        for (let y = 0; y < tileYNum; y++) {
            // 타일 중심 좌표 계산
            const tileX = tileXZeroPoint - (x * xCoordinateHorizontal * 1.2) + (y * yCoordinateHorizontal * 0.88);
            const tileY = tileYZeroPoint + (x * xCoordinateVertical * 1.2) + (y * yCoordinateVertical * 0.88);

            // 타일의 4개 꼭지점 계산
            const corners = [
                { x: tileX, y: tileY }, // 왼쪽 상단
                { x: tileX + xCoordinateHorizontal, y: tileY + xCoordinateVertical }, // 오른쪽 상단
                { x: tileX + xCoordinateHorizontal - yCoordinateHorizontal, y: tileY + xCoordinateVertical + yCoordinateVertical }, // 오른쪽 하단
                { x: tileX - yCoordinateHorizontal, y: tileY + yCoordinateVertical } // 왼쪽 하단
            ];

            // 타일의 시각적 경계 저장
            gridTiles.push({
                x: tileX - yCoordinateHorizontal, // 가장 왼쪽 x 좌표
                y: tileY, // 가장 위쪽 y 좌표
                width: xCoordinateHorizontal + yCoordinateHorizontal, // 너비 계산
                height: xCoordinateVertical + yCoordinateVertical, // 높이 계산
                corners: corners, // 마우스 충돌 감지용 꼭지점
                centerX: tileX,
                centerY: tileY
            });

            // 타일 색상 설정 (호버 상태에 따라)
            ctx.fillStyle = hoveredTileIndex === gridTiles.length - 1
                ? "rgba(255, 200, 100, 0.5)"  // 호버 강조 색상
                : "rgba(200, 200, 200, 0.3)"; // 일반 타일 색상

            ctx.strokeStyle = "rgba(150, 150, 150, 0.5)";

            // 다이아몬드 형태 타일 그리기
            ctx.beginPath();
            ctx.moveTo(corners[0].x, corners[0].y); // 왼쪽 상단
            ctx.lineTo(corners[1].x, corners[1].y); // 오른쪽 상단
            ctx.lineTo(corners[2].x, corners[2].y); // 오른쪽 하단
            ctx.lineTo(corners[3].x, corners[3].y); // 왼쪽 하단
            ctx.closePath();

            ctx.fill();
            ctx.stroke();
        }
    }
}

/**
 * 정보 박스 그리기 함수
 */
function drawInfoBox(machine, mouseX, mouseY, isSelected = false) {
    // 재료 정보가 있을 경우 추가 높이 계산
    const materialsCount = machine.materials ? machine.materials.length : 0;
    const baseHeight = 60; // 기본 높이 (머신 ID, 라인 정보)
    const materialItemHeight = 20; // 재료 아이템 하나당 높이
    const materialsHeaderHeight = materialsCount > 0 ? 20 : 0; // "Materials:" 헤더 높이

    // 선택된 상태일 경우 버튼 영역 추가
    const buttonsHeight = isSelected ? 40 : 0;

    const boxHeight = baseHeight + materialsHeaderHeight + (materialsCount * materialItemHeight) + buttonsHeight;
    const boxWidth = 200;
    const padding = 10;

    // 정보 상자 위치 계산 (마우스 위치 기준)
    let boxX = mouseX + 15; // 마우스 커서로부터 약간 오른쪽
    let boxY = mouseY - boxHeight - 5; // 마우스 커서 위쪽

    // 박스가 캔버스 경계를 벗어나는지 확인하고 위치 조정
    if (boxX + boxWidth > canvas.width) {
        boxX = mouseX - boxWidth - 15; // 왼쪽에 표시
    }

    if (boxY < 0) {
        boxY = mouseY + 15; // 마우스 아래에 표시
    }

    // 배경 그리기
    ctx.fillStyle = "rgba(255, 255, 255, 0.9)";
    ctx.strokeStyle = "#333";
    ctx.lineWidth = 1;

    // 둥근 모서리 사각형 그리기
    ctx.beginPath();
    ctx.roundRect(boxX, boxY, boxWidth, boxHeight, 5);
    ctx.fill();
    ctx.stroke();

    // 텍스트 작성
    ctx.fillStyle = "#000";
    ctx.font = "14px Arial";
    ctx.fillText(`Machine ID: ${machine.machine_id}`, boxX + padding, boxY + padding + 15);
    ctx.fillText(`Line: ${machine.line}`, boxX + padding, boxY + padding + 35);

    // 재료 정보 작성
    if (machine.materials && machine.materials.length > 0) {
        ctx.font = "12px Arial";
        ctx.fillText("Materials:", boxX + padding, boxY + padding + 55);

        machine.materials.forEach((material, idx) => {
            const yPos = boxY + padding + 55 + materialsHeaderHeight + (idx * materialItemHeight);
            ctx.fillText(`${material.materialCode}: ${material.stock} units`, boxX + padding + 10, yPos);
        });
    }

    // 선택된 상태일 때 버튼 추가
    if (isSelected) {
        const buttonY = boxY + boxHeight - buttonsHeight + 10;
        const modifyBtnWidth = 80;
        const deleteBtnWidth = 80;
        const btnHeight = 25;
        const btnGap = 10;

        // Modify 버튼
        ctx.strokeStyle = "#198754"; // 녹색 테두리
        ctx.beginPath();
        ctx.roundRect(boxX + padding, buttonY, modifyBtnWidth, btnHeight, 3);
        ctx.stroke();

        // Delete 버튼
        ctx.strokeStyle = "#dc3545"; // 빨간색 테두리
        ctx.beginPath();
        ctx.roundRect(boxX + padding + modifyBtnWidth + btnGap, buttonY, deleteBtnWidth, btnHeight, 3);
        ctx.stroke();

        // 버튼 텍스트
        ctx.fillStyle = "#FFFFFF"; // 흰색 텍스트
        ctx.font = "12px Arial";

        // Modify 버튼 텍스트
        ctx.fillStyle = "#198754";
        ctx.fillText("Modify", boxX + padding + 20, buttonY + 17);

        // Delete 버튼 텍스트
        ctx.fillStyle = "#dc3545";
        ctx.fillText("Delete", boxX + padding + modifyBtnWidth + btnGap + 20, buttonY + 17);

        // 버튼 영역 저장 (버튼 클릭 감지를 위해)
        machine.modifyButton = {
            x: boxX + padding,
            y: buttonY,
            width: modifyBtnWidth,
            height: btnHeight
        };

        machine.deleteButton = {
            x: boxX + padding + modifyBtnWidth + btnGap,
            y: buttonY,
            width: deleteBtnWidth,
            height: btnHeight
        };
    }

    // 정보 상자 위치와 크기 저장 (클릭 이벤트 처리용)
    machine.infoBox = {
        x: boxX,
        y: boxY,
        width: boxWidth,
        height: boxHeight
    };
}

/**
 * 마우스 위치가 다이아몬드 타일 내부에 있는지 확인
 */
function isPointInDiamond(px, py, tile) {
    const corners = tile.corners;

    // 다이아몬드 내부 포인트 체크 알고리즘
    let inside = false;

    // 교차점 알고리즘 사용
    for (let i = 0, j = corners.length - 1; i < corners.length; j = i++) {
        const xi = corners[i].x, yi = corners[i].y;
        const xj = corners[j].x, yj = corners[j].y;

        const intersect = ((yi > py) !== (yj > py))
            && (px < (xj - xi) * (py - yi) / (yj - yi) + xi);

        if (intersect) {
            inside = !inside;
        }
    }

    return inside;
}

/**
 * 캔버스 클릭 이벤트 핸들러
 */
function handleCanvasClick(event) {
    const x = event.offsetX;
    const y = event.offsetY;

    // 이벤트가 캔버스 외부에서 발생한 경우 무시
    if (x < 0 || x > canvas.width || y < 0 || y > canvas.height) {
        return;
    }

    // 이미 선택된 머신이 있고 선택된 머신의 정보 상자 내에서 클릭한 경우
    if (selectedMachineIndex !== null && imagePositions[selectedMachineIndex]) {
        const machine = imagePositions[selectedMachineIndex];

        // 정보 상자 내부 클릭 확인
        if (machine.infoBox &&
            x >= machine.infoBox.x &&
            x <= machine.infoBox.x + machine.infoBox.width &&
            y >= machine.infoBox.y &&
            y <= machine.infoBox.y + machine.infoBox.height) {

            // Modify 버튼 클릭 확인
            if (machine.modifyButton &&
                x >= machine.modifyButton.x &&
                x <= machine.modifyButton.x + machine.modifyButton.width &&
                y >= machine.modifyButton.y &&
                y <= machine.modifyButton.y + machine.modifyButton.height) {

                // Modify 버튼 클릭 이벤트 처리
                console.log("Modify button clicked for machine:", machine.machine_id);

                const event = new CustomEvent('machineModify', { detail: { machineId: machine.machine_id } });
                document.dispatchEvent(event);

                return; // 이벤트 전파 중단
            }

            // Delete 버튼 클릭 확인
            if (machine.deleteButton &&
                x >= machine.deleteButton.x &&
                x <= machine.deleteButton.x + machine.deleteButton.width &&
                y >= machine.deleteButton.y &&
                y <= machine.deleteButton.y + machine.deleteButton.height) {

                // Delete 버튼 클릭 이벤트 처리
                console.log("Delete button clicked for machine:", machine.machine_id);

                const event = new CustomEvent('machineDelete', { detail: { machineId: machine.machine_id } });
                document.dispatchEvent(event);

                return; // 이벤트 전파 중단
            }

            // 정보 상자 내의 다른 영역 클릭 시 - 선택 상태 유지
            event.stopPropagation(); // 이벤트 버블링 중단
            return;
        }
    }

    // 다른 영역 클릭 - 머신 선택 확인
    let clicked = false;

    // 머신 영역 클릭 확인
    for (let i = 0; i < imagePositions.length; i++) {
        const position = imagePositions[i];
        if (
            x >= position.x && x <= position.x + position.width &&
            y >= position.y && y <= position.y + position.height
        ) {
            // 현재 선택된 머신과 같은 머신을 클릭한 경우 선택 해제
            if (selectedMachineIndex === i) {
                selectedMachineIndex = null;
            } else {
                // 다른 머신 선택
                selectedMachineIndex = i;
            }
            clicked = true;

            // 화면 다시 그리기
            const currentData = lineSelector && lineSelector.value !== "all"
                ? factoryData.filter(machine => machine.line === lineSelector.value)
                : factoryData;
            renderAll(currentData);
            break;
        }
    }

    // 머신 영역 외 클릭 - 선택 해제
    if (!clicked) {
        selectedMachineIndex = null;

        // 화면 다시 그리기
        const currentData = lineSelector && lineSelector.value !== "all"
            ? factoryData.filter(machine => machine.line === lineSelector.value)
            : factoryData;
        renderAll(currentData);
    }
}

/**
 * 문서 클릭 이벤트 핸들러 - 캔버스 외부 클릭 처리
 */
function handleDocumentClick(event) {
    // 캔버스 내부 클릭은 handleCanvasClick에서 처리
    if (event.target === canvas) {
        return;
    }

    // 캔버스 외부 클릭 시 선택 해제
    if (selectedMachineIndex !== null) {
        selectedMachineIndex = null;

        // 화면 다시 그리기
        const currentData = lineSelector && lineSelector.value !== "all"
            ? factoryData.filter(machine => machine.line === lineSelector.value)
            : factoryData;
        renderAll(currentData);
    }
}

/**
 * 마우스 이벤트 핸들러
 */
function handleMouseMove(event) {
    lastMouseX = event.offsetX;
    lastMouseY = event.offsetY;

    let needsRedraw = false;

    // 이전 타일/머신 호버 상태 저장
    const previousHoveredTileIndex = hoveredTileIndex;
    const previousHoveredMachineIndex = hoveredMachineIndex;

    // 타일 호버 체크 (다이아몬드 형태 고려)
    hoveredTileIndex = null;
    for (let i = 0; i < gridTiles.length; i++) {
        const tile = gridTiles[i];

        // 향상된 다이아몬드 충돌 검사 사용
        if (isPointInDiamond(lastMouseX, lastMouseY, tile)) {
            hoveredTileIndex = i;
            break;
        }
    }

    // 머신 호버 체크 (선택된 머신이 없을 때만)
    if (selectedMachineIndex === null) {
        hoveredMachineIndex = null;
        for (let i = 0; i < imagePositions.length; i++) {
            const position = imagePositions[i];
            if (
                lastMouseX >= position.x && lastMouseX <= position.x + position.width &&
                lastMouseY >= position.y && lastMouseY <= position.y + position.height
            ) {
                hoveredMachineIndex = i;
                break;
            }
        }
    }

    // 호버 상태가 변경되었는지 확인
    if (previousHoveredTileIndex !== hoveredTileIndex ||
        previousHoveredMachineIndex !== hoveredMachineIndex) {
        needsRedraw = true;
    }

    // 변경이 있을 때만 다시 그리기
    if (needsRedraw) {
        const currentData = lineSelector && lineSelector.value !== "all"
            ? factoryData.filter(machine => machine.line === lineSelector.value)
            : factoryData;

        renderAll(currentData);
    }
}

/**
 * 전체 화면 렌더링 통합 함수
 */
function renderAll(data = factoryData) {
    // 캔버스를 한 번만 지움
    ctx.clearRect(0, 0, canvas.width, canvas.height);

    // 배경 그리드 그리기
    drawGrid();

    // 이미지 위치 초기화
    imagePositions.length = 0;

    // 머신 이미지 그리기
    data.forEach((item, index) => {
        // 그리드와 동일한 계수 사용하여 일관성 유지
        const imageX = tileXZeroPoint - (item.x * xCoordinateHorizontal * 1.2) + (item.y * yCoordinateHorizontal * 0.88);
        const imageY = tileYZeroPoint + (item.x * xCoordinateVertical * 1.2) + (item.y * yCoordinateVertical * 0.88);

        // 이미지 위치 정보 저장
        imagePositions.push({
            x: imageX - (tileXSize / 2), // 이미지 중앙 정렬
            y: imageY - (tileYSize / 2), // 이미지 중앙 정렬
            width: tileXSize,
            height: tileYSize,
            machine_id: item.machine_id,
            line: item.line,
            materials: item.materials,
            index: index
        });

        // 이미지 그리기
        if (loadedImages[item.type]) {
            ctx.drawImage(
                loadedImages[item.type],
                imageX - (tileXSize / 2),
                imageY - (tileYSize / 2),
                tileXSize,
                tileYSize
            );
        } else {
            // 이미지가 없을 경우 대체 표시
            ctx.fillStyle = "#8899AA";
            ctx.fillRect(imageX - (tileXSize / 2), imageY - (tileYSize / 2), tileXSize, tileYSize);
            ctx.fillStyle = "#FFFFFF";
            ctx.font = "12px Arial";
            ctx.fillText(item.machine_id, imageX - (tileXSize / 4), imageY);
        }
    });

    // 선택된 머신 정보 상자 그리기 (가장 먼저 그려서 다른 항목 위에 표시)
    if (selectedMachineIndex !== null && imagePositions[selectedMachineIndex]) {
        drawInfoBox(
            imagePositions[selectedMachineIndex],
            imagePositions[selectedMachineIndex].x + tileXSize / 2,
            imagePositions[selectedMachineIndex].y + tileYSize / 2,
            true // 선택된 상태
        );
    }
    // 호버된 머신 정보 상자 그리기 (선택된 머신이 없을 때만)
    else if (hoveredMachineIndex !== null && imagePositions[hoveredMachineIndex]) {
        drawInfoBox(imagePositions[hoveredMachineIndex], lastMouseX, lastMouseY, false);
    }
}

/**
 * 초기 렌더링 함수 - 이미지 로딩 및 첫 렌더링
 */
async function renderFactory(data = factoryData) {
    try {
        // 기계 이미지 로딩 시도
        loadedImages = {}; // 이미지 초기화
        for (const type in blockImages) {
            try {
                loadedImages[type] = await loadImage(blockImages[type]);
            } catch (err) {
                console.error(`${type} 이미지 로드 실패:`, err);
            }
        }

        // 이미지가 하나도 로드되지 않았다면 대체 방법 사용
        const hasImages = Object.keys(loadedImages).length > 0;
        if (!hasImages) {
            console.warn("이미지가 로드되지 않아 대체 방식으로 렌더링합니다");
        }

        // 초기 렌더링
        renderAll(data);
    } catch (error) {
        console.error("렌더링 중 오류 발생:", error);
    }
}

// 기계 GUI 정보 로드
async function loadMachineGuiInfo() {
    try {
        const response = await fetch('/api/productProcessManagement/machine/guiInfo');
        if (!response.ok) {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        const guiInfoData = await response.json();
        window.machineGuiInfoJson = guiInfoData;

        // GUI 업데이트
        if (typeof updateFactoryGUI === 'function') {
            updateFactoryGUI(guiInfoData);
        }

        return guiInfoData;
    } catch (error) {
        console.error('기계 GUI 정보 로드 실패:', error);
        alert('기계 GUI 정보를 불러오는 중 오류가 발생했습니다.');
    }
}

document.addEventListener('machineModify', function(e) {
    const machineId = e.detail.machineId;
    console.log('머신 수정 이벤트 발생:', machineId);

    // 머신 정보 가져오기
    fetch(`/api/productProcessManagement/modifyMachine/${machineId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('머신 정보를 가져오는데 실패했습니다.');
            }
            return response.json();
        })
        .then(machineData => {
            console.log('가져온 머신 정보:', machineData);
            // 모달 폼에 데이터 채우기
            fillModifyForm(machineData);
            // 모달 표시
            $('#modifyMachineModal').modal('show');
        })
        .catch(error => {
            console.error('머신 정보 가져오기 오류:', error);
            alert('머신 정보를 가져오는 중 오류가 발생했습니다.');
        });
});

function fillModifyForm(machineData) {
    const machineIdInput = document.querySelector('#modify-machine-id');
    const xCoordinateSelect = document.querySelector('#modify-x-coordinate');
    const yCoordinateSelect = document.querySelector('#modify-y-coordinate');
    const machineTypeSelect = document.querySelector('#modify-machine-type');

    // 머신 ID 설정 (읽기 전용)
    machineIdInput.value = machineData.machineId;

    // X 좌표 옵션 초기화 및 설정
    xCoordinateSelect.innerHTML = '';
    for (let x = 0; x < tileXNum; x++) {
        const option = document.createElement('option');
        option.value = x;
        option.textContent = x;
        if (x === machineData.xCoordinate) {
            option.selected = true;
        }
        xCoordinateSelect.appendChild(option);
    }

    // Y 좌표 옵션 초기화 및 설정
    yCoordinateSelect.innerHTML = '';
    for (let y = 0; y < tileYNum; y++) {
        const option = document.createElement('option');
        option.value = y;
        option.textContent = y;
        if (y === machineData.yCoordinate) {
            option.selected = true;
        }
        yCoordinateSelect.appendChild(option);
    }

    // 머신 타입 옵션 초기화 및 설정
    machineTypeSelect.innerHTML = '';
    Object.keys(blockImages).forEach(type => {
        const option = document.createElement('option');
        option.value = type;
        option.textContent = type.replace('_', ' ');
        if (type === machineData.machineType) {
            option.selected = true;
        }
        machineTypeSelect.appendChild(option);
    });
}

document.addEventListener('DOMContentLoaded', function() {
    const modifyForm = document.querySelector('#modifyMachineForm');

    if (modifyForm) {
        modifyForm.addEventListener('submit', function(e) {
            e.preventDefault();

            const machineId = document.querySelector('#modify-machine-id').value;
            const xCoordinate = parseInt(document.querySelector('#modify-x-coordinate').value);
            const yCoordinate = parseInt(document.querySelector('#modify-y-coordinate').value);
            const machineType = document.querySelector('#modify-machine-type').value;

            // guiId를 설정하지 않았는데, backend에서 machineId로 처리한다면 문제 없음
            const updateData = {
                machineId: machineId,
                xCoordinate: xCoordinate,
                yCoordinate: yCoordinate,
                machineType: machineType
            };

            // POST 요청으로 업데이트
            fetch('/api/productProcessManagement/modifyMachine/update', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updateData)
            })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(text => {
                        throw new Error(text || '머신 업데이트 중 오류가 발생했습니다.');
                    });
                }
                return response.text();
            })
            .then(result => {
                alert(result || '머신 정보가 성공적으로 업데이트되었습니다.');
                // 모달 닫기
                $('#modifyMachineModal').modal('hide');
                // 페이지 새로고침 또는 데이터 갱신
                location.reload();
            })
            .catch(error => {
                console.error('머신 업데이트 오류:', error);
                alert(error.message || '머신 정보 업데이트 중 오류가 발생했습니다.');
            });
        });
    }
});

document.addEventListener('machineDelete', function(e) {
    const machineId = e.detail.machineId;
    console.log('머신 삭제 이벤트 발생:', machineId);

    // 삭제 확인 메시지
    if (confirm(`정말로 "${machineId}" 머신을 삭제하시겠습니까?`)) {
        // 삭제 API 호출
        fetch(`/api/productProcessManagement/deleteMachine/delete/${machineId}`, {
            method: 'DELETE'
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text || '머신 삭제 중 오류가 발생했습니다.');
                });
            }
            return response.text();
        })
        .then(result => {
            alert(result || '머신이 성공적으로 삭제되었습니다.');
            // 페이지 새로고침 또는 데이터 갱신
            location.reload();
        })
        .catch(error => {
            console.error('머신 삭제 오류:', error);
            alert(error.message || '머신 삭제 중 오류가 발생했습니다.');
        });
    }
});

/*
    Add Machine 버튼 Modal 옵션 추가
*/
const machineIdSelector = document.querySelector('#add-machine-machine-id');
const xCoordinateSelector = document.querySelector('#add-machine-x-coordinate');
const yCoordinateSelector = document.querySelector('#add-machine-y-coordinate');
const machineTypeSelector = document.querySelector('#add-machine-machine-type');

document.addEventListener("DOMContentLoaded", function() {
    $.get('/api/productProcessManagement/addMachine/machineList', function(response) {
        if (Array.isArray(response)) {  // 배열인지 확인
            response.forEach(machineId => {
                const option = document.createElement('option');
                option.value = machineId;
                option.textContent = machineId;
                machineIdSelector.appendChild(option);
            });
        } else {
            console.error("Invalid response format:", response);
        }
    }).fail(function(error) {
        console.error("Error fetching machine list:", error);
    });

    for(let xCoor = 0; xCoor < tileXNum; xCoor++) {
        let option = document.createElement("option");
        option.value = xCoor;
        option.textContent = xCoor;

        xCoordinateSelector.appendChild(option);
    }

    for(let yCoor = 0; yCoor < tileYNum; yCoor++) {
        let option = document.createElement("option");
        option.value = yCoor;
        option.textContent = yCoor;

        yCoordinateSelector.appendChild(option);
    }

    Object.keys(blockImages).forEach(machineType => {
        const option = document.createElement('option');
        option.value = machineType;
        option.textContent = machineType.replace("_", " ");
        machineTypeSelector.appendChild(option);
    });

    loadProductionTargetRatio();
});

/*
    add 버튼 클릭시
*/
document.addEventListener("DOMContentLoaded", function() {
    const addButton = document.querySelector('#machineAddButton');


    addButton.addEventListener("click", function() {
        const machineId = machineIdSelector.value;
        const xCoordinate = xCoordinateSelector.value;
        const yCoordinate = yCoordinateSelector.value;
        const machineType = machineTypeSelector.value;

        if (!machineId || !xCoordinate || !yCoordinate || !machineType) {
            alert("모든 값을 선택해야 합니다.");
            return;
        }

        const requestData = {
            guiId: null,
            machineId: machineId,
            xCoordinate: parseInt(xCoordinate),
            yCoordinate: parseInt(yCoordinate),
            machineType: machineType
        };

        $.ajax({
            url: '/api/productProcessManagement/addMachine/add',
            type: 'POST',
            contentType: 'application/json',
            data: JSON.stringify(requestData),
            success: function(response) {
                alert("기계가 성공적으로 추가되었습니다!");
                location.reload();
            },
            error: function(xhr, status, error) {
                console.log("상태 코드:", xhr.status);
                console.log("오류 메시지:", xhr.responseText);
                console.log("에러 상세:", error);
                alert("오류 발생: " + xhr.responseText);
            }
        });
    });
});

/*
    Production Target Ratio Radial Chart
*/
var productionTargetRatioCharts = [];
var productionTargetRatioData = [];

document.addEventListener("DOMContentLoaded", function() {
    // 라인 선택기가 있는 경우, 이벤트 리스너 설정
    const lineTargetSelector = document.getElementById("lineTargetSelector");
    if (lineTargetSelector) {
        lineTargetSelector.addEventListener("change", function() {
            renderProductionTargetChart(this.value);
        });
    }

    // 초기 데이터 로드
    loadProductionTargetRatio();
});

// 생산 목표 비율 데이터 가져오기
async function loadProductionTargetRatio() {
    try {
        const response = await fetch('/api/productProcessManagement/machine/targetRatio');
        if (!response.ok) {
            throw new Error('서버 응답 오류: ' + response.status);
        }

        const targetRatioData = await response.json();
        productionTargetRatioData = targetRatioData;

        // 현재 선택된 라인으로 차트 렌더링
        const selectedLine = lineSelector ? lineSelector.value : '';
        renderProductionTargetChart(selectedLine);

        return targetRatioData;
    } catch (error) {
        console.error('생산 목표 비율 데이터 로드 실패:', error);
        alert('생산 목표 비율 데이터를 불러오는 중 오류가 발생했습니다.');
    }
}

// 라인별로 데이터 그룹화
function groupProductionDataByLine(data) {
    const lineData = {};

    // "all" 그룹 추가
    lineData["all"] = data;

    // 라인별로 데이터 그룹화 (product_name에서 첫 두 글자로 라인 구분 예: "L1_XXX")
    data.forEach(item => {
        // 제품명에서 라인 정보 추출 (예: L1, L2 등)
        const linePart = item.productName.split('_')[0];

        if (!lineData[linePart]) {
            lineData[linePart] = [];
        }

        lineData[linePart].push(item);
    });

    return lineData;
}

// 라인 선택기 업데이트
function updateLineTargetSelector(lines) {
    const lineTargetSelector = document.getElementById("lineTargetSelector");
    if (!lineTargetSelector) return;

    // 기존 옵션 제거
    lineTargetSelector.innerHTML = '';

    // 'all' 옵션 추가
    const allOption = document.createElement('option');
    allOption.value = 'all';
    allOption.textContent = '모든 라인';
    lineTargetSelector.appendChild(allOption);

    // 라인 옵션 추가
    lines.forEach(line => {
        if (line === 'all') return; // all은 이미 추가했으므로 건너뛰기

        const option = document.createElement('option');
        option.value = line;
        option.textContent = `${line} 라인`;
        lineTargetSelector.appendChild(option);
    });
}

// 생산 목표 차트 렌더링
function renderProductionTargetChart(lineId) {
    const chartContainer = document.getElementById('production-target-ratio-chart');
    if (!chartContainer) return;

    // 기존 차트 정리
    productionTargetRatioCharts.forEach(chart => {
        if (chart) {
            chart.destroy();
        }
    });
    productionTargetRatioCharts = [];

    // 차트 컨테이너 비우기
    chartContainer.innerHTML = '';

    // 선택된 라인에 따라 데이터 필터링
    let filteredData = productionTargetRatioData.filter(item => item.lineCode === lineId);

    if (filteredData.length === 0) {
        chartContainer.innerHTML = '<div class="alert alert-info">해당 라인의 데이터가 없습니다.</div>';
        return;
    }

    // 데이터를 카드 형태로 나누어 표시
    filteredData.forEach((item, index) => {
        // 생산 목표 대비 실제 생산량 비율 계산
        const ratio = item.productionTarget > 0
            ? Math.round((item.quantity / item.productionTarget) * 100)
            : 0;

        // 차트를 담을 카드 생성
        const cardDiv = document.createElement('div');
        cardDiv.className = 'col-md-12 mb-3';

        cardDiv.innerHTML = `
            <div class="card">
                <div class="card-body">
                    <h5 class="card-title">${item.productName}</h5>
                    <div id="target-chart-${index}" class="mt-2"></div>
                    <div class="text-center mt-2">
                        <small>생산량: ${item.quantity} / 목표: ${item.productionTarget}</small>
                    </div>
                </div>
            </div>
        `;

        chartContainer.appendChild(cardDiv);

        const chartOptions = {
            series: [ratio],
            chart: {
                height: 250,
                type: 'radialBar',
            },
            plotOptions: {
                radialBar: {
                    hollow: {
                        size: '70%',
                    },
                    dataLabels: {
                        name: {
                            show: false,
                        },
                        value: {
                            fontSize: '22px',
                            show: true,
                            formatter: function(val) {
                                return val + '%';
                            }
                        }
                    },
                    track: {
                        background: '#f2f2f2',
                    }
                }
            },
            fill: {
                type: 'gradient',
                gradient: {
                    shade: 'dark',
                    type: 'horizontal',
                    shadeIntensity: 0.5,
                    gradientToColors: ['#ABE5A1'],
                    inverseColors: true,
                    opacityFrom: 1,
                    opacityTo: 1,
                    stops: [0, 100]
                }
            },
            stroke: {
                lineCap: 'round'
            },
            colors: [getColorByRatio(ratio)],
            labels: ['달성률']
        };

        // 차트 생성 및 렌더링
        const chart = new ApexCharts(document.getElementById(`target-chart-${index}`), chartOptions);
        chart.render();
        productionTargetRatioCharts.push(chart);
    });
}

// 비율에 따른 색상 반환
function getColorByRatio(ratio) {
    if (ratio >= 100) return '#00E396'; // 목표 달성 (녹색)
    if (ratio >= 80) return '#26a0fc';  // 80% 이상 (파란색)
    if (ratio >= 50) return '#FEB019';  // 50% 이상 (주황색)
    return '#FF4560';                   // 50% 미만 (빨간색)
}

// 생산 목표 차트 업데이트
function updateProductionTargetChart() {
    // lineSelector에서 현재 선택된 라인 가져오기
    const selectedLine = lineSelector ? lineSelector.value : '';
    const chartLine = selectedLine === "" ? "all" : selectedLine;

    // 데이터 다시 로드
    loadProductionTargetRatio().then(() => {
        // 선택된 라인으로 차트 다시 렌더링
        renderProductionTargetChart(chartLine);
    });
}

