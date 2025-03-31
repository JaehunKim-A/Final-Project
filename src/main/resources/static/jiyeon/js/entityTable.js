document.addEventListener("DOMContentLoaded", function () {

    const entity = document.body.dataset.entity || "unknown";
    const label = document.body.dataset.label || "항목";
    const fields = JSON.parse(document.body.dataset.fields || "[]");
    const accordionFields = JSON.parse(document.body.dataset.accordionFields || "[]");
    const table = document.querySelector("#table1");

    const utils = {
        capitalize: str => str.charAt(0).toUpperCase() + str.slice(1),
        getIdElement: (prefix) => document.getElementById(`${prefix}${utils.capitalize(entity)}Id`),
        getFormElement: (prefix) => document.getElementById(`${prefix}${utils.capitalize(entity)}Form`),
        setInputValue: (id, value) => {
            const el = document.getElementById(id);
            if (el) el.value = value;
        },
        renderDataAttrs: (data, fields) => fields.map(f => `data-${f}="${data[f] || ''}"`).join(" ")
    };

    const dataTable = new simpleDatatables.DataTable(table, {
        perPage: 10,
        searchable: true,
        sortable: true,
        labels: {
                placeholder: `${label} 를(을) 검색`,
                perPage: `{select}개씩 보기`,
                noRows: `${label}을 찾을 수 없습니다.`,
                info: `현재 페이지 ${label} {start}~{end} / 전체:{rows}`,
                },
    });

    const adaptPageDropdown = () => {
        const selector = dataTable.wrapper.querySelector(".dataTable-selector");
        if (selector) {
            selector.parentNode.parentNode.insertBefore(selector, selector.parentNode);
            selector.classList.add("form-select");
        }
    };

    const adaptPagination = () => {
        const paginations = dataTable.wrapper.querySelectorAll("ul.dataTable-pagination-list");
        paginations.forEach(pagination => {
            pagination.classList.add("pagination", "pagination-primary");
        });

        const items = dataTable.wrapper.querySelectorAll("ul.dataTable-pagination-list li");
        items.forEach(li => li.classList.add("page-item"));

        const links = dataTable.wrapper.querySelectorAll("ul.dataTable-pagination-list li a");
        links.forEach(link => link.classList.add("page-link"));
    };

    const refreshPagination = () => adaptPagination();

    dataTable.on("datatable.init", () => {
        adaptPageDropdown();
        refreshPagination();
    });
    dataTable.on("datatable.update", refreshPagination);
    dataTable.on("datatable.sort", refreshPagination);
    dataTable.on("datatable.page", adaptPagination);

    table.addEventListener("click", (e) => {
        const editBtn = e.target.closest(".btn-edit");
        if (editBtn) {
            const id = editBtn.dataset.id;
            utils.setInputValue(`edit${utils.capitalize(entity)}Id`, id);

            fields.forEach(field => {
                utils.setInputValue(`edit${utils.capitalize(field)}`, editBtn.dataset[field.toLowerCase()] || "");
            });

            utils.setInputValue("editRegDate", editBtn.dataset.reg || "");
            utils.setInputValue("editModDate", editBtn.dataset.mod || "");
            utils.setInputValue("editPhoneNumber", editBtn.dataset.phoneNumber || "");
            return;
        }

        const deleteBtn = e.target.closest(".btn-delete");
        if (deleteBtn) {
            utils.setInputValue(`delete${utils.capitalize(entity)}Id`, deleteBtn.dataset.id);
            return;
        }

        const detailCell = e.target.closest(`.${entity}-detail-toggle`);
        if (detailCell) toggleAccordionRow(detailCell.closest("tr"));
    });

    function toggleAccordionRow(row) {
        const nextRow = row.nextElementSibling;
        if (nextRow?.classList.contains("accordion-row")) {
            bootstrap.Collapse.getOrCreateInstance(nextRow.querySelector(".accordion-collapse")).hide();
            nextRow.remove();
            return;
        }

        const data = {};
        accordionFields.forEach(f => {
            const el = row.querySelector(`.${entity}-${f}`) || row.querySelector(`.${f}`);
            data[f] = el?.innerHTML?.trim() || "";
        });

        const rowIndex = row.rowIndex;
        const accordionRow = document.createElement("tr");
        accordionRow.classList.add("accordion-row");
        accordionRow.innerHTML = renderAccordionRow(data, rowIndex);
        row.parentNode.insertBefore(accordionRow, row.nextSibling);

        new bootstrap.Collapse(document.getElementById(`collapse-${rowIndex}`), { toggle: true });
    }

    function renderAccordionRow(data, index) {
        const detailHTML = accordionFields.map(f => `<td>${data[f]}</td>`).join("");
        const buttonAttrs = utils.renderDataAttrs(data, fields);
        return `
        <td colspan="99">
            <div class="accordion" id="accordionDetail-${index}">
                <div class="accordion-item">
                    <div id="collapse-${index}" class="accordion-collapse collapse">
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
                                                data-id="${data.id}" ${buttonAttrs}
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
            </div>
        </td>`;
    }

    // 수정 및 삭제 form 처리
    setupFormSubmit("edit", "POST", `수정 완료`, `수정 실패`);
    setupDeleteForm();

    // CSV 업로드 처리
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
            location.href = `/table/${entity}`;
        }).catch(err => {
            alert("CSV 업로드 중 오류 발생");
            console.error(err);
        });
    });

    function setupFormSubmit(prefix, method, successMsg, errorMsg) {
        const form = utils.getFormElement(prefix);
        form?.addEventListener("submit", function (e) {
            e.preventDefault();
            const id = utils.getIdElement(prefix)?.value;
            const formData = new FormData(form);
            fetch(`/table/${entity}/edit/${id}`, {
                method,
                body: formData
            }).then(res => {
                if (!res.ok) throw new Error("요청 실패");
                alert(`${label} ${successMsg}`);
                location.reload();
            }).catch(err => {
                alert(`${label} ${errorMsg}`);
                console.error(err);
            });
        });
    }

    function setupDeleteForm() {
        const deleteForm = utils.getFormElement("delete");
        deleteForm?.addEventListener("submit", function (e) {
            e.preventDefault();
            const id = utils.getIdElement("delete")?.value;
            location.href = `/table/${entity}/delete/${id}`;
        });
    }
});
