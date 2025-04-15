document.addEventListener("DOMContentLoaded", function() {

    const tableBody = document.querySelector("#table1");
    const myDataTable = new simpleDatatables.DataTable(tableBody, {
        perPage: 10,
        searchable: true,
        sortable: true,
        labels: {
            placeholder: "코드을(를) 검색하세요...",
            perPage: "{select} 행을 한 페이지에 표시",
            noRows: "코드을(를) 찾을 수 없습니다.",
            info: "전체 {rows}개의 코드 중 {start}에서 {end}까지 표시",
        },
    });

    // 수정 버튼 클릭 (이벤트 위임)
    document.querySelector("#table1").addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-edit");
        if (btn) {
            const codeId = btn.getAttribute("data-id");
            const codeValue = btn.getAttribute("data-value");
            const codeName = btn.getAttribute("data-name");
            const codeType = btn.getAttribute("data-type");
            const category = btn.getAttribute("data-category");
            const description = btn.getAttribute("data-description");

            document.getElementById("editCodeId").value = codeId;
            document.getElementById("editCodeValue").value = codeValue;
            document.getElementById("editCodeName").value = codeName;
            document.getElementById("editCodeType").value = codeType;
            document.getElementById("editCategory").value = category;
            document.getElementById("editDescription").value = description;
        }
    });

    const editCodeManagementForm = document.getElementById("editCodeManagementForm");
    editCodeManagementForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const formData = new FormData(editCodeManagementForm);
        const codeId = document.getElementById("editCodeId").value;
        fetch(`/codeManagement/codeManagement/edit/${codeId}`, {
            method: "POST",
            body: formData,
        })
        .then((response) => {
            if (response.ok) {
                alert("코드 정보가 수정되었습니다.");
                location.reload();
            } else {
                alert("코드 수정에 실패했습니다.");
            }
        })
        .catch((error) => {
            alert("에러 발생: 코드 수정 실패");
            console.error("에러:", error);
        });
    });

    document.querySelector("#table1").addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-delete");
        if (btn) {
            const codeId = btn.getAttribute("data-id");
            console.log("선택된 코드 ID:", codeId); // 디버깅용
            document.getElementById("deleteCodeId").value = codeId;
        }
    });

    // 삭제 폼 제출 → GET 요청으로 리다이렉트
    const deleteCodeManagementForm = document.getElementById("deleteCodeManagementForm");
    deleteCodeManagementForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const codeId = document.getElementById("deleteCodeId").value;

        if (!codeId) {
            alert("코드 ID가 없습니다.");
            return;
        }

        location.href = `/codeManagement/codeManagement/delete/${codeId}`;
    });
});

  function openProductPopup(button) {
    let popup = open("/finishedProduct/searchPopup", "Product Search", "width=650,height=400");

    // 팝업에서 데이터를 받아오는 이벤트 처리
    addEventListener("message", function(event) {
        if (event.origin !== location.origin) return;

        const productData = event.data;
        if (productData && productData.unit) {
            document.getElementById('codeValue').value = productData.unit;
            document.getElementById('editCodeValue').value = productData.unit;
        }
    }, {
        once: true
    });
}
function openMaterialPopup(button) {
    let popup = open("/rawMaterial/searchPopup", "Material Search", "width=650,height=400");

    // 팝업에서 데이터를 받아오는 이벤트 처리
    addEventListener("message", function(event) {
        if (event.origin !== location.origin) return;

        const materialData = event.data;
        if (materialData && materialData.unit) {
            document.getElementById('codeValue').value = materialData.unit;
            document.getElementById('editCodeValue').value = materialData.unit;
        }
    }, {
        once: true
    });
}