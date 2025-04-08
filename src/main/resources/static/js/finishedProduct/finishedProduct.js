document.addEventListener("DOMContentLoaded", function() {

    const tableBody = document.querySelector("#table1");
    const myDataTable = new simpleDatatables.DataTable(tableBody, {
        perPage: 10,
        searchable: true,
        sortable: true,
        labels: {
            placeholder: "제품을(를) 검색하세요...",
            perPage: "{select} 행을 한 페이지에 표시",
            noRows: "제품을(를) 찾을 수 없습니다.",
            info: "전체 {rows}개의 제품 중 {start}에서 {end}까지 표시",
        },
    });

    // 수정 버튼 클릭 (이벤트 위임)
    document.querySelector("#table1").addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-edit");
        if (btn) {
            const productId = btn.getAttribute("data-id");
            const productCode = btn.getAttribute("data-code");
            const productName = btn.getAttribute("data-name");
            const category = btn.getAttribute("data-category");
            const unit = btn.getAttribute("data-unit");
            const status = btn.getAttribute("data-status");
            const description = btn.getAttribute("data-description");

            document.getElementById("editProductId").value = productId;
            document.getElementById("editProductCode").value = productCode;
            document.getElementById("editProductName").value = productName;
            document.getElementById("editCategory").value = category;
            document.getElementById("editUnit").value = unit;
            document.getElementById("editStatus").value = status;
            document.getElementById("editDescription").value = description;
        }
    });

    const editFinishedProductForm = document.getElementById("editFinishedProductForm");
    editFinishedProductForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const formData = new FormData(editFinishedProductForm);
        const productId = document.getElementById("editProductId").value;
        fetch(`/finishedProduct/finishedProduct/edit/${productId}`, {
            method: "POST",
            body: formData,
        })
        .then((response) => {
            if (response.ok) {
                alert("제품 정보가 수정되었습니다.");
                location.reload();
            } else {
                alert("제품 수정에 실패했습니다.");
            }
        })
        .catch((error) => {
            alert("에러 발생: 제품 수정 실패");
            console.error("에러:", error);
        });
    });

    document.querySelector("#table1").addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-delete");
        if (btn) {
            const productId = btn.getAttribute("data-id");
            console.log("선택된 제품 ID:", productId); // 디버깅용
            document.getElementById("deleteProductId").value = productId;
        }
    });

    // 삭제 폼 제출 → GET 요청으로 리다이렉트
    const deleteFinishedProductForm = document.getElementById("deleteFinishedProductForm");
    deleteFinishedProductForm.addEventListener("submit", (event) => {
        event.preventDefault();
        const productId = document.getElementById("deleteProductId").value;

        if (!productId) {
            alert("제품 ID가 없습니다.");
            return;
        }

        location.href = `/finishedProduct/finishedProduct/delete/${productId}`;
    });
});