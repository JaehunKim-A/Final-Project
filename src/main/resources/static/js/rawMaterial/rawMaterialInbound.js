document.addEventListener("DOMContentLoaded", () => {
    const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
    const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
    const apiUrl = "/raw-material/inbound/api/list";
    const tableBody = document.getElementById("inboundTableBody");
    const pagination = document.getElementById("inbound-pagination");
    const pageInfo = document.getElementById("inbound-page-info");

    let currentPage = 0; // 백엔드는 0부터 시작
    let pageSize = 10;
    let keyword = "";
    let sortBy = "inboundId";
    let direction = "asc";

    // 초기 데이터 로딩
    fetchAndRender();

    // 검색 버튼
    document.getElementById("searchButton").addEventListener("click", () => {
        keyword = document.getElementById("searchKeyword").value;
        currentPage = 0;
        fetchAndRender();
    });

    // 페이지 크기 변경
    document.getElementById("inbound-page-size").addEventListener("change", (e) => {
        pageSize = parseInt(e.target.value);
        currentPage = 0;
        fetchAndRender();
    });

    // 정렬 처리
    document.querySelectorAll(".dataTable-sorter").forEach(sorter => {
        sorter.addEventListener("click", (e) => {
            e.preventDefault();
            const newSortBy = e.target.getAttribute("data-sort");

            if (sortBy === newSortBy) {
                direction = direction === "asc" ? "desc" : "asc";
            } else {
                sortBy = newSortBy;
                direction = "asc";
            }

            fetchAndRender();
        });
    });

    // 등록
    document.getElementById("registerForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const form = e.target;
        const data = {
            inboundId: parseInt(form.inboundId.value),
            inboundCode: form.inboundCode.value,
            materialCode: form.materialCode.value,
            quantity: parseInt(form.quantity.value),
            inboundDate: form.inboundDate.value,
            status: form.status.value,
        };

        try {
            const response = await fetch("/raw-material/inbound/api/register", {
                method: "POST",
                headers: {
                 "Content-Type": "application/json",
                [header]: token
                 },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                form.reset();
                const modal = bootstrap.Modal.getInstance(document.getElementById("registerModal"));
                modal.hide();
                fetchAndRender();
            } else {
                const error = await response.json();
                alert("등록 실패: " + (error.message || "알 수 없는 오류가 발생했습니다."));
            }
        } catch (error) {
            console.error("등록 중 오류 발생:", error);
            alert("등록 중 오류가 발생했습니다.");
        }
    });

    // 수정
    document.getElementById("editForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const form = e.target;
        const inboundId = parseInt(form.editInboundId.value);

        const data = {
            inboundId: inboundId,
            inboundCode: form.editInboundCode.value,
            materialCode: form.editMaterialCode.value,
            quantity: parseInt(form.editQuantity.value),
            inboundDate: form.editInboundDate.value,
            status: form.editStatus.value
        };

        try {
            const response = await fetch(`/raw-material/inbound/api/update/${inboundId}`, {
                method: "PUT",
                headers: { "Content-Type": "application/json", [header]: token },
                body: JSON.stringify(data)
            });

            if (response.ok) {
                const modal = bootstrap.Modal.getInstance(document.getElementById("editModal"));
                modal.hide();
                fetchAndRender();
            } else {
                const error = await response.json();
                alert("수정 실패: " + (error.message || "알 수 없는 오류가 발생했습니다."));
            }
        } catch (error) {
            console.error("수정 중 오류 발생:", error);
            alert("수정 중 오류가 발생했습니다.");
        }
    });

    // 삭제
    document.getElementById("deleteForm").addEventListener("submit", async (e) => {
        e.preventDefault();
        const inboundId = document.getElementById("deleteId").value;

        try {
            const response = await fetch(`/raw-material/inbound/api/delete/${inboundId}`, {
                method: "DELETE",
                headers: { "Content-Type": "application/json", [header]: token }
            });

            if (response.ok) {
                const modal = bootstrap.Modal.getInstance(document.getElementById("deleteModal"));
                modal.hide();
                fetchAndRender();
            } else {
                const error = await response.json();
                alert("삭제 실패: " + (error.message || "알 수 없는 오류가 발생했습니다."));
            }
        } catch (error) {
            console.error("삭제 중 오류 발생:", error);
            alert("삭제 중 오류가 발생했습니다.");
        }
    });

    // 데이터 fetch & 렌더링
    async function fetchAndRender() {
        try {
            // GET 방식으로 변경
            const url = new URL(apiUrl, window.location.origin);
            url.searchParams.append('page', currentPage);
            url.searchParams.append('size', pageSize);
            url.searchParams.append('sortBy', sortBy);
            url.searchParams.append('direction', direction);
            if (keyword) {
                url.searchParams.append('keyword', keyword);
            }

            const response = await fetch(url);

            if (!response.ok) {
                throw new Error("데이터를 불러오는데 실패했습니다.");
            }

            const result = await response.json();
            renderTable(result.dtoList || result.content);
            renderPagination(result.totalPage || result.totalPages);

            // 페이지 정보 표시 형식 수정 (백엔드가 0부터 시작하는 경우 +1)
            const currentPageDisplay = (result.page !== undefined) ? result.page : currentPage + 1;
            const totalItems = result.totalCount || result.totalItems;
            const totalPages = result.totalPage || result.totalPages;

            renderPageInfo(currentPageDisplay, totalItems, totalPages);
        } catch (error) {
            console.error("데이터 로딩 중 오류 발생:", error);
            alert("데이터를 불러오는데 실패했습니다.");
        }
    }

    function renderTable(list) {
        if (!list || list.length === 0) {
            tableBody.innerHTML = `<tr><td colspan="8" class="text-center">데이터가 없습니다.</td></tr>`;
            return;
        }

        tableBody.innerHTML = "";
        list.forEach(item => {
            const row = document.createElement("tr");
            row.innerHTML = `
                <td>${item.inboundId}</td>
                <td>${item.inboundCode}</td>
                <td>${item.materialCode || '-'}</td>
                <td>${item.quantity}</td>
                <td>${formatDate(item.inboundDate)}</td>
                <td>
                    <span class="badge bg-${getStatusColor(item.status)}">${item.status}</span>
                </td>
                <td>
                    <button class="btn btn-sm btn-primary me-1" onclick="openEditModal(${JSON.stringify(item).replace(/"/g, '&quot;')})">
                        <i class="bi bi-pencil-square"></i> 수정
                    </button>
                    <button class="btn btn-sm btn-danger" onclick="openDeleteModal(${item.inboundId})">
                        <i class="bi bi-trash"></i> 삭제
                    </button>
                </td>
            `;
            tableBody.appendChild(row);
        });
    }

    function getStatusColor(status) {
        switch(status?.toLowerCase()) {
            case 'complete': return 'success';
            case 'pending': return 'warning';
            case 'cancelled': return 'danger';
            default: return 'secondary';
        }
    }

    function renderPagination(totalPages) {
        pagination.innerHTML = "";

        // 이전 페이지 버튼
        const prevLi = document.createElement("li");
        prevLi.className = `page-item ${currentPage === 0 ? "disabled" : ""}`;
        prevLi.innerHTML = `<a class="page-link" href="#">이전</a>`;
        prevLi.addEventListener("click", (e) => {
            e.preventDefault();
            if (currentPage > 0) {
                currentPage--;
                fetchAndRender();
            }
        });
        pagination.appendChild(prevLi);

        // 페이지 버튼들
        const maxVisiblePages = 5;
        let startPage = Math.max(0, currentPage - Math.floor(maxVisiblePages / 2));
        let endPage = Math.min(totalPages - 1, startPage + maxVisiblePages - 1);

        if (endPage - startPage + 1 < maxVisiblePages) {
            startPage = Math.max(0, endPage - maxVisiblePages + 1);
        }

        for (let i = startPage; i <= endPage; i++) {
            const li = document.createElement("li");
            li.className = `page-item ${i === currentPage ? "active" : ""}`;
            li.innerHTML = `<a class="page-link" href="#">${i + 1}</a>`;
            li.addEventListener("click", (e) => {
                e.preventDefault();
                currentPage = i;
                fetchAndRender();
            });
            pagination.appendChild(li);
        }

        // 다음 페이지 버튼
        const nextLi = document.createElement("li");
        nextLi.className = `page-item ${currentPage >= totalPages - 1 ? "disabled" : ""}`;
        nextLi.innerHTML = `<a class="page-link" href="#">다음</a>`;
        nextLi.addEventListener("click", (e) => {
            e.preventDefault();
            if (currentPage < totalPages - 1) {
                currentPage++;
                fetchAndRender();
            }
        });
        pagination.appendChild(nextLi);
    }

    function renderPageInfo(page, total, totalPages) {
        pageInfo.innerText = `페이지 ${page}/${totalPages} | 총 ${total}건`;
    }

    function formatDate(dateStr) {
        if (!dateStr) return '-';

        try {
            const date = new Date(dateStr);
            return date.toLocaleString('ko-KR', {
                year: 'numeric',
                month: '2-digit',
                day: '2-digit',
                hour: '2-digit',
                minute: '2-digit'
            });
        } catch (e) {
            return dateStr; // 변환 실패 시 원본 문자열 반환
        }
    }
});

// 전역에서 사용 가능한 모달 함수
function openEditModal(item) {
//    document.getElementById("editId").value = item.inboundId;
    document.getElementById("editInboundId").value = item.inboundId;
    document.getElementById("editInboundCode").value = item.inboundCode;
    document.getElementById("editMaterialCode").value = item.materialCode || '';
    document.getElementById("editQuantity").value = item.quantity;

    // ISO 형식으로 변환하여 datetime-local 입력에 적합하게 설정
    if (item.inboundDate) {
        const date = new Date(item.inboundDate);
        if (!isNaN(date.getTime())) {
            // YYYY-MM-DDThh:mm 형식으로 변환
            const localDatetime = date.toISOString().slice(0, 16);
            document.getElementById("editInboundDate").value = localDatetime;
        }
    }

    document.getElementById("editStatus").value = item.status;
    new bootstrap.Modal(document.getElementById("editModal")).show();
}

function openDeleteModal(id) {
    document.getElementById("deleteId").value = id;
    new bootstrap.Modal(document.getElementById("deleteModal")).show();
}