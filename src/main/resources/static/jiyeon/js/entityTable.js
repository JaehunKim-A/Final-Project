document.addEventListener("DOMContentLoaded", function () {
    const entity = document.body.dataset.entity;
    const label = document.body.dataset.label;
    const fields = JSON.parse(document.body.dataset.fields);
    const accordionFields = JSON.parse(document.body.dataset.accordionFields);
    const table = document.querySelector("#table1");

    new simpleDatatables.DataTable(table, {
        perPage: 10,
        searchable: true,
        sortable: true,
        labels: {
            placeholder: `${label}을 검색하세요...`,
            perPage: "{select} 행을 한 페이지에 표시",
            noRows: `${label}을 찾을 수 없습니다.`,
            info: `전체 {rows}개의 ${label} 중 {start}에서 {end}까지 표시`,
        },
    });

    // 수정 버튼
    table.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-edit");
        if (!btn) return;

        const id = btn.dataset.id;
        document.getElementById(`edit${capitalize(entity)}Id`).value = id;

        fields.forEach(field => {
            const value = btn.dataset[field.toLowerCase()] || "";
            const input = document.getElementById(`edit${capitalize(field)}`);
            if (input) input.value = value;
        });

        document.getElementById("editRegDate").value = btn.dataset.reg || "";
        document.getElementById("editModDate").value = btn.dataset.mod || "";

        const phoneInput = document.getElementById("editPhoneNumber");

        if (phoneInput) phoneInput.value = btn.dataset.phoneNumber;
    });

    // 삭제 버튼
    table.addEventListener("click", (e) => {
        const btn = e.target.closest(".btn-delete");
        if (!btn) return;
        const id = btn.dataset.id;
        document.getElementById(`delete${capitalize(entity)}Id`).value = id;
    });

    // 수정 form 전송
    const editForm = document.getElementById(`edit${capitalize(entity)}Form`);
    editForm?.addEventListener("submit", function (e) {
        e.preventDefault();
        const id = document.getElementById(`edit${capitalize(entity)}Id`).value;
        const formData = new FormData(editForm);
        fetch(`/table/${entity}/edit/${id}`, {
            method: "POST",
            body: formData
        }).then(res => {
            if (!res.ok) throw new Error("수정 실패");
            alert(`${label} 정보가 수정되었습니다.`);
            location.reload();
        }).catch(err => {
            alert(`${label} 수정 실패`);
            console.error(err);
        });
    });

    // 삭제 form 전송
    const deleteForm = document.getElementById(`delete${capitalize(entity)}Form`);
    deleteForm?.addEventListener("submit", function (e) {
        e.preventDefault();
        const id = document.getElementById(`delete${capitalize(entity)}Id`).value;
        location.href = `/table/${entity}/delete/${id}`;
    });

    // accordion 상세 보기
    table.addEventListener("click", function (e) {
        const detailCell = e.target.closest(`.${entity}-detail-toggle`);
        if (!detailCell) return;

        const row = detailCell.closest("tr");
        const nextRow = row.nextElementSibling;

        if (nextRow && nextRow.classList.contains("accordion-row")) {
            const collapseEl = nextRow.querySelector(".accordion-collapse");
            if (collapseEl) {
                const instance = bootstrap.Collapse.getOrCreateInstance(collapseEl);
                instance.hide();
            }
            nextRow.remove();
            return;
        }

       const data = {};
       accordionFields.forEach(f => {
           const selector = `.${entity}-${f}`;
           let el = row.querySelector(selector) || row.querySelector(`.${f}`);
           if (!el && row.nextElementSibling) {
               el = row.nextElementSibling.querySelector(selector) || row.nextElementSibling.querySelector(`.${f}`);
           }
           data[f] = el?.innerHTML?.trim() || "";
       });



        const accordionId = `accordionDetail-${row.rowIndex}`;
        const collapseId = `collapse-${row.rowIndex}`;

        let detailHTML = accordionFields.map(f =>
            `<td>${data[f]}</td>`
        ).join("");

        const buttonFields = fields
            .filter(f => data[f] !== undefined && data[f] !== null)
            .map(f => `data-${f}="${data[f]}"`)
            .join(" ");

        const accordionHTML = `
            <div class="accordion" id="${accordionId}">
                <div class="accordion-item">
                    <div id="${collapseId}" class="accordion-collapse collapse" aria-labelledby="heading-${row.rowIndex}">
                        <div class="accordion-body p-0">
                            <table class="table table-bordered mb-0 align-middle">
                                <thead>
                                    <tr>
                                        ${accordionFields.map(f => `<th>${f}</th>`).join("")}
                                        <th>수정/삭제</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        ${detailHTML}
                                        <td>
                                            <button class="btn btn-sm btn-outline-primary btn-edit"
                                                data-bs-toggle="modal" data-bs-target="#editModal"
                                                data-id="${data.id}"
                                                ${buttonFields}
                                                data-reg="${data.reg || ''}"
                                                data-mod="${data.mod || ''}">
                                                Edit
                                            </button>
                                            <button class="btn btn-sm btn-outline-info btn-delete"
                                                data-bs-toggle="modal" data-bs-target="#deleteModal"
                                                data-id="${data.id}">Delete</button>
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>`;

        const accordionRow = document.createElement("tr");
        accordionRow.classList.add("accordion-row");
        accordionRow.innerHTML = `<td colspan="99">${accordionHTML}</td>`;
        row.parentNode.insertBefore(accordionRow, row.nextSibling);

        const collapseEl = document.getElementById(collapseId);
        if (collapseEl) {
            new bootstrap.Collapse(collapseEl, { toggle: true });
        }
    });

    // CSV 업로드
    const csvUploadForm = document.getElementById("csvUploadForm");
    csvUploadForm?.addEventListener("submit", function (e) {
        e.preventDefault();
        const formData = new FormData(csvUploadForm);
        fetch(`/upload/csv/${entity}`, {
            method: "POST",
            body: formData
        }).then(res => {
            if (!res.ok) throw new Error("업로드 실패");
            return res.text();
        }).then(() => {
            alert(`${label} CSV 업로드 완료`);
            window.location.href = `/table/${entity}`;
        }).catch(err => {
            alert("CSV 업로드 중 오류");
            console.error(err);
        });
    });

    function capitalize(str) {
        return str.charAt(0).toUpperCase() + str.slice(1);
    }
});
