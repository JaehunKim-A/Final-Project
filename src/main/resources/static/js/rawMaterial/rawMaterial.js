document.addEventListener("DOMContentLoaded", function() {
    // 데이터 테이블 초기화
    initDataTable();

    // 이벤트 리스너 등록
    setupEditButtonListeners();
    setupDeleteButtonListeners();
    setupFormSubmitListeners();
});

// 데이터 테이블 초기화 함수
function initDataTable() {
    const tableBody = document.querySelector("#table1");
    const myDataTable = new simpleDatatables.DataTable(tableBody, {
        perPage: 10,
        searchable: true,
        sortable: true,
        labels: {
            placeholder: "원자재를 검색하세요...",
            perPage: "{select} 행을 한 페이지에 표시",
            noRows: "원자재를 찾을 수 없습니다.",
            info: "전체 {rows}개의 원자재 중 {start}에서 {end}까지 표시",
        },
    });
}

// 수정 버튼 이벤트 리스너 설정
function setupEditButtonListeners() {
    document.querySelector("#table1").addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-edit");
        if (btn) {
            const materialId = btn.getAttribute("data-id");
            const materialCode = btn.getAttribute("data-code");
            const materialName = btn.getAttribute("data-name");
            const category = btn.getAttribute("data-category");
            const unit = btn.getAttribute("data-unit");
            const description = btn.getAttribute("data-description");

            document.getElementById("editMaterialId").value = materialId;
            document.getElementById("editMaterialCode").value = materialCode;
            document.getElementById("editMaterialName").value = materialName;
            document.getElementById("editCategory").value = category;
            document.getElementById("editUnit").value = unit;
            document.getElementById("editDescription").value = description;
        }
    });
}

// 삭제 버튼 이벤트 리스너 설정
function setupDeleteButtonListeners() {
    document.querySelector("#table1").addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-delete");
        if (btn) {
            const materialId = btn.getAttribute("data-id");
            console.log("선택된 원자재 ID:", materialId); // 디버깅용
            document.getElementById("deleteMaterialId").value = materialId;
        }
    });
}

// 폼 제출 이벤트 리스너 설정
function setupFormSubmitListeners() {
    // 수정 폼 제출 처리
    const editRawMaterialForm = document.getElementById("editRawMaterialForm");
    if (editRawMaterialForm) {
        editRawMaterialForm.addEventListener("submit", handleEditFormSubmit);
    }

    // 삭제 폼 제출 처리
    const deleteRawMaterialForm = document.getElementById("deleteRawMaterialForm");
    if (deleteRawMaterialForm) {
        deleteRawMaterialForm.addEventListener("submit", handleDeleteFormSubmit);
    }
}

// 수정 폼 제출 핸들러
function handleEditFormSubmit(event) {
    event.preventDefault();
    const formData = new FormData(this);
    const materialId = document.getElementById("editMaterialId").value;

    fetch(`/rawMaterial/rawMaterial/edit/${materialId}`, {
        method: "POST",
        body: formData,
    })
    .then((response) => {
        if (response.ok) {
            alert("원자재 정보가 수정되었습니다.");
            location.reload();
        } else {
            alert("원자재 수정에 실패했습니다.");
        }
    })
    .catch((error) => {
        alert("에러 발생: 원자재 수정 실패");
        console.error("에러:", error);
    });
}

// 삭제 폼 제출 핸들러
function handleDeleteFormSubmit(event) {
    event.preventDefault();
    const materialId = document.getElementById("deleteMaterialId").value;

    if (!materialId) {
        alert("원자재 ID가 없습니다.");
        return;
    }

    location.href = `/rawMaterial/rawMaterial/delete/${materialId}`;
}