document.addEventListener("DOMContentLoaded", () => {
  const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
  let currentPage = 1;
  const keywordInput = document.querySelector("#searchKeyword");
  const searchBtn = document.querySelector("#searchButton");
  const tableBody = document.querySelector("#outboundTableBody");
  const paginationContainer = document.querySelector("#outbound-pagination");
  const pageSizeSelector = document.querySelector("#outbound-page-size");

  // 최초 로딩
  fetchOutboundList(currentPage);

  // 검색 버튼 클릭 이벤트
  searchBtn.addEventListener("click", () => {
    currentPage = 1;
    fetchOutboundList(currentPage, keywordInput.value.trim());
  });

  // 페이지 사이즈 변경 이벤트
  pageSizeSelector.addEventListener("change", () => {
    currentPage = 1;
    fetchOutboundList(currentPage, keywordInput.value.trim());
  });

  // 데이터 가져오기 함수
  async function fetchOutboundList(page, keyword = "") {
    const pageSize = parseInt(pageSizeSelector.value);

    try {
      const response = await fetch("/raw-material/outbound/api/list", {
        method: "POST",
        headers: { "Content-Type": "application/json", [header]: token },
        body: JSON.stringify({ page, size: pageSize, keyword }),
      });

      if (!response.ok) throw new Error(`HTTP error! status: ${response.status}`);

      const data = await response.json();
      console.log("Fetched data:", data); // ✅ 콘솔 로그 추가

      renderTable(data.dtoList || []);
      renderPagination(data, { page, size: pageSize }); // ✅ 파라미터 수정
    } catch (error) {
      console.error("Error fetching data:", error);
    }
  }

  // 테이블 렌더링
  function renderTable(list) {
    tableBody.innerHTML = "";

    if (!Array.isArray(list) || list.length === 0) {
      tableBody.innerHTML = `<tr><td colspan="8" class="text-center">데이터가 없습니다.</td></tr>`;
      return;
    }

    list.forEach(item => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${item.outboundId ?? "-"}</td>
        <td>${item.outboundCode ?? "-"}</td>
        <td>${item.outboundDate ?? "-"}</td>
        <td>${item.materialId ?? "-"}</td>
        <td>${item.quantity ?? "-"}</td>
        <td>${item.status ?? "-"}</td>
        <td>${item.warehouse ?? "-"}</td>
        <td>
          <button class="btn btn-sm btn-primary" onclick="openEditModal(${item.outboundId})">수정</button>
          <button class="btn btn-sm btn-danger" onclick="openDeleteModal(${item.outboundId})">삭제</button>
        </td>
      `;
      tableBody.appendChild(row);
    });
  }

  // 페이지네이션 렌더링 함수
  function renderPagination(data, state) {
    const pagination = document.getElementById('outbound-pagination');

    const totalItems = data.total || 0;
    const totalPages = Math.ceil(totalItems / state.size);
    const currentPage = state.page;

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
      link.addEventListener('click', function (e) {
        e.preventDefault();

        if (this.parentElement.classList.contains('disabled')) return;

        const pageNum = parseInt(this.getAttribute('data-page'));

        if (!isNaN(pageNum) && pageNum > 0 && pageNum <= totalPages && pageNum !== currentPage) {
          fetchOutboundList(pageNum, keywordInput.value.trim()); // ✅ 실제 호출 함수로 연결
        }
      });
    });
  }

  // 수정 모달 열기
  window.openEditModal = async (outboundId) => {
    try {
      const response = await fetch(`/raw-material/outbound/api/${outboundId}`);
      const data = await response.json();

      document.querySelector("#editOutboundId").value = data.outboundId;
      document.querySelector("#editMaterialId").value = data.materialId;
      document.querySelector("#editOutboundCode").value = data.outboundCode;
      document.querySelector("#editOutboundDate").value = data.outboundDate;
      document.querySelector("#editQuantity").value = data.quantity;
      document.querySelector("#editWarehouse").value = data.warehouse;
      document.querySelector("#editStatus").value = data.status;

      new bootstrap.Modal(document.querySelector("#editModal")).show();
    } catch (err) {
      console.error("수정 모달 오류:", err);
    }
  };

  // 삭제 모달 열기
  window.openDeleteModal = (outboundId) => {
    document.querySelector("#deleteId").value = outboundId;
    new bootstrap.Modal(document.querySelector("#deleteModal")).show();
  };

  // 등록 처리
  document.querySelector("#registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;

    const payload = {
      materialId: form.materialId.value,
      outboundCode: form.outboundCode.value,
      outboundDate: form.outboundDate.value,
      quantity: form.quantity.value,
      warehouse: form.warehouse.value,
      status: form.status.value,
    };

    try {
      const response = await fetch("/raw-material/outbound/api/register", {
        method: "POST",
        headers: { "Content-Type": "application/json", [header]: token },
        body: JSON.stringify(payload),
      });

      if (!response.ok) throw new Error("등록 실패");

      bootstrap.Modal.getInstance(document.querySelector("#registerModal")).hide();
      fetchOutboundList(currentPage, keywordInput.value.trim());
      form.reset();
    } catch (err) {
      console.error("등록 오류:", err);
    }
  });

  // 수정 처리
  document.querySelector("#editForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;

    const payload = {
      outboundId: form.editId.value,
      materialId: form.editMaterialId.value,
      outboundCode: form.editOutboundCode.value,
      outboundDate: form.editOutboundDate.value,
      quantity: form.editQuantity.value,
      warehouse: form.editWarehouse.value,
      status: form.editStatus.value,
    };

    try {
      const response = await fetch("/raw-material/outbound/api/modify", {
        method: "PUT",
        headers: { "Content-Type": "application/json", [header]: token },
        body: JSON.stringify(payload),
      });

      if (!response.ok) throw new Error("수정 실패");

      bootstrap.Modal.getInstance(document.querySelector("#editModal")).hide();
      fetchOutboundList(currentPage, keywordInput.value.trim());
    } catch (err) {
      console.error("수정 오류:", err);
    }
  });

  // 삭제 처리
  document.querySelector("#deleteForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const outboundId = document.querySelector("#deleteId").value;

    try {
      const response = await fetch(`/raw-material/outbound/api/delete/${outboundId}`, {
        method: "DELETE",
        headers: { [header]: token },
      });

      if (!response.ok) throw new Error("삭제 실패");

      bootstrap.Modal.getInstance(document.querySelector("#deleteModal")).hide();
      fetchOutboundList(currentPage, keywordInput.value.trim());
    } catch (err) {
      console.error("삭제 오류:", err);
    }
  });
});
