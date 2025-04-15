document.addEventListener("DOMContentLoaded", () => {
  const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
  const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');
  let currentPage = 1;
  const keywordInput = document.querySelector("#searchKeyword");
  const searchBtn = document.querySelector("#searchButton");
  const tableBody = document.querySelector("#inboundTableBody");
  const paginationContainer = document.querySelector("#inbound-pagination");
  const pageSizeSelector = document.querySelector("#inbound-page-size");

  // 최초 로딩
  fetchInboundList(currentPage);

  // 검색 버튼 클릭 이벤트
  searchBtn.addEventListener("click", () => {
    currentPage = 1;
    fetchInboundList(currentPage, keywordInput.value.trim());
  });

  // 페이지 사이즈 변경 이벤트
  pageSizeSelector.addEventListener("change", () => {
    currentPage = 1;
    fetchInboundList(currentPage, keywordInput.value.trim());
  });

  // 데이터 가져오기 함수
  async function fetchInboundList(page, keyword = "") {
    const pageSize = parseInt(pageSizeSelector.value);

    try {
      const response = await fetch("/raw-material/inbound/api/list", {
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
      tableBody.innerHTML = `<tr><td colspan="6" class="text-center">데이터가 없습니다.</td></tr>`;
      return;
    }

    list.forEach(item => {
      const row = document.createElement("tr");
      row.innerHTML = `
        <td>${item.inboundId ?? "-"}</td>
        <td>${item.materialId ?? "-"}</td>
        <td>${item.inboundCode ?? "-"}</td>
        <td>${item.materialCode ?? "-"}</td>
        <td>${item.quantity ?? "-"}</td>
        <td>${item.inboundDate ?? "-"}</td>
        <td>${item.status ?? "-"}</td>
        <td>
          <button class="btn btn-sm btn-primary" onclick="openEditModal(${item.inboundId})">수정</button>
          <button class="btn btn-sm btn-danger" onclick="openDeleteModal(${item.inboundId})">삭제</button>
        </td>
      `;
      tableBody.appendChild(row);
    });
  }

  // 페이지네이션 렌더링 함수
  function renderPagination(data, state) {
    const pagination = document.getElementById('inbound-pagination');

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
          fetchInboundList(pageNum, keywordInput.value.trim()); // ✅ 실제 호출 함수로 연결
        }
      });
    });
  }

  // 수정 모달 열기
  window.openEditModal = async (inboundId) => {
    try {
      const response = await fetch(`/raw-material/inbound/api/${inboundId}`);
      const data = await response.json();

      document.querySelector("#editId").value = data.inboundId;
      document.querySelector("#editmaterialId").value = data.materialId;
      document.querySelector("#editInboundCode").value = data.inboundCode;
      document.querySelector("#editMaterialCode").value = data.materialCode;
      document.querySelector("#editQuantity").value = data.quantity;
      document.querySelector("#editInboundDate").value = data.inboundDate;
      document.querySelector("#editStatus").value = data.status;

      new bootstrap.Modal(document.querySelector("#editModal")).show();
    } catch (err) {
      console.error("수정 모달 오류:", err);
    }
  };

  // 삭제 모달 열기
  window.openDeleteModal = (inboundId) => {
    document.querySelector("#deleteId").value = inboundId;
    new bootstrap.Modal(document.querySelector("#deleteModal")).show();
  };

  // 등록 처리
  document.querySelector("#registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const form = e.target;

    const payload = {
      inboundId: form.inboundId.Value,
      materialId: form.materialId.Value,
      inboundCode: form.inboundCode.value,
      materialCode: form.materialCode.value,
      quantity: form.quantity.value,
      inboundDate: form.inboundDate.value,
      status: form.status.value,
    };

    try {
      const response = await fetch("/raw-material/inbound/api/register", {
        method: "POST",
        headers: { "Content-Type": "application/json", [header]: token },
        body: JSON.stringify(payload),
      });

      if (!response.ok) throw new Error("등록 실패");

      bootstrap.Modal.getInstance(document.querySelector("#registerModal")).hide();
      fetchInboundList(currentPage, keywordInput.value.trim());
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

      inboundId: form.editId.value,
      materialId: form.editMaterialId.Value,
      inboundCode: form.editInboundCode.value,
      materialCode: form.editMaterialCode.value,
      quantity: form.editQuantity.value,
      inboundDate: form.editInboundDate.value,
      status: form.editStatus.value,

    };

    try {
      const response = await fetch("/raw-material/inbound/api/modify", {
        method: "PUT",
        headers: { "Content-Type": "application/json", [header]: token },
        body: JSON.stringify(payload),
      });

      if (!response.ok) throw new Error("수정 실패");

      bootstrap.Modal.getInstance(document.querySelector("#editModal")).hide();
      fetchInboundList(currentPage, keywordInput.value.trim());
    } catch (err) {
      console.error("수정 오류:", err);
    }
  });

  // 삭제 처리
  document.querySelector("#deleteForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const inboundId = document.querySelector("#deleteId").value;

    try {
      const response = await fetch(`/raw-material/inbound/api/delete/${inboundId}`, {
        method: "DELETE",
      });

      if (!response.ok) throw new Error("삭제 실패");

      bootstrap.Modal.getInstance(document.querySelector("#deleteModal")).hide();
      fetchInboundList(currentPage, keywordInput.value.trim());
    } catch (err) {
      console.error("삭제 오류:", err);
    }
  });
});
