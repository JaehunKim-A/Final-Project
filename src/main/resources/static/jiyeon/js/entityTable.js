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
                placeholder: `검색(검색어1 검색어2 ..)`,
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

        const selector = dataTable.wrapper.querySelector(".dataTable-selector");
        if (selector) {
            const optionAll = document.createElement("option");
            optionAll.value = "all";
            optionAll.textContent = "전체";
            selector.appendChild(optionAll);

            selector.addEventListener("change", () => {
                if (selector.value === "all") {
                    const totalRows = dataTable.data.length || 1000;
                    dataTable.options.perPage = totalRows;
                    dataTable.update();
                }
            });
        }
        table.classList.remove("invisible");
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
            const id = deleteBtn.dataset.id;
            const name = deleteBtn.dataset.name || "선택된 고객"; // data-name 속성 활용
            utils.setInputValue(`delete${utils.capitalize(entity)}Id`, id);

            const msg = document.getElementById("deleteConfirmMessage");
            if (msg) msg.textContent = `정말로 ${name} 님을 삭제하시겠습니까?`;
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
        const accordionLabels = JSON.parse(document.body.dataset.accordionLabels || "[]");
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
                                            ${
                                                accordionFields.map((f, i) =>
                                                    `<th>${accordionLabels[i] || f}</th>`
                                                ).join("")
                                            }
                                            <th>수정/삭제</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            ${
                                                accordionFields.map(f =>
                                                    `<td>${data[f]}</td>`
                                                ).join("")
                                            }
                                            <td>
                                                <button class="btn btn-sm btn-outline-primary btn-edit"
                                                    data-bs-toggle="modal" data-bs-target="#editModal"
                                                    data-id="${data.id}" ${buttonAttrs}
                                                    data-reg="${data.reg || ''}"
                                                    data-mod="${data.mod || ''}">
                                                    수정
                                                </button>
                                                <button class="btn btn-sm btn-outline-info btn-delete"
                                                    data-bs-toggle="modal" data-bs-target="#deleteModal"
                                                    data-id="${data.id}" data-name="${data.name}">
                                                    삭제
                                                </button>
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

    document.getElementById("downloadVisibleExcel")?.addEventListener("click", () => {
        const visibleRows = table.querySelectorAll("tbody tr:not(.accordion-row)");

        // entity별 필드 클래스 및 헤더 정의
        const exportConfig = {
            customer: {
                headers: ["코드", "이름", "연락처", "주소", "등록일", "수정일"],
                classes: ["customer-id", "customer-name", "customer-contact", "customer-address", "customer-reg", "customer-mod"],
                sheetName: "고객 목록",
                fileName: "고객_현재보기.xlsx"
            },
            rawMaterialSupplier: {
                headers: ["코드", "이름", "연락처", "주소", "이메일", "전화번호", "등록일", "수정일"],
                classes: ["rawMaterialSupplier-id", "rawMaterialSupplier-name", "rawMaterialSupplier-contact", "rawMaterialSupplier-address", "rawMaterialSupplier-email", "rawMaterialSupplier-phone", "rawMaterialSupplier-reg", "rawMaterialSupplier-mod"],
                sheetName: "공급업체 목록",
                fileName: "공급업체_현재보기.xlsx"
            }
            // 필요한 다른 entity 추가 가능
        };

        const config = exportConfig[entity];
        if (!config) {
            alert(`${label} 엑셀 다운로드 구성이 없습니다.`);
            return;
        }

        const data = [config.headers];

        visibleRows.forEach(row => {
            if (row.offsetParent === null) return;
            const rowData = config.classes.map(cls => row.querySelector(`.${cls}`)?.innerText.trim() || "");
            if (rowData.every(cell => !cell)) return;
            data.push(rowData);
        });

        const worksheet = XLSX.utils.aoa_to_sheet(data);
        const workbook = XLSX.utils.book_new();
        XLSX.utils.book_append_sheet(workbook, worksheet, config.sheetName);

        XLSX.writeFile(workbook, config.fileName);
    });

   document.getElementById("downloadVisibleCsv")?.addEventListener("click", () => {
       const visibleRows = table.querySelectorAll("tbody tr:not(.accordion-row)");

       const exportConfig = {
           customer: {
               headers: ["코드", "이름", "연락처", "주소", "등록일", "수정일"],
               classes: ["customer-id", "customer-name", "customer-contact", "customer-address", "customer-reg", "customer-mod"],
               fileName: "고객_검색결과.csv",
               sheetName: "고객 목록"
           },
           rawMaterialSupplier: {
               headers: ["코드", "이름", "연락처", "주소", "이메일", "전화번호", "등록일", "수정일"],
               classes: ["rawMaterialSupplier-id", "rawMaterialSupplier-name", "rawMaterialSupplier-contact", "rawMaterialSupplier-address", "rawMaterialSupplier-email", "rawMaterialSupplier-phone", "rawMaterialSupplier-reg", "rawMaterialSupplier-mod"],
               fileName: "공급업체_검색결과.csv",
               sheetName: "공급업체 목록"
           }
       };

       const config = exportConfig[entity];
       if (!config) return alert(`${label} CSV 다운로드 구성이 없습니다.`);

       const data = [config.headers];
       visibleRows.forEach(row => {
           if (row.offsetParent === null) return;
           const rowData = config.classes.map(cls => row.querySelector(`.${cls}`)?.innerText.trim() || "");
           if (rowData.every(cell => !cell)) return;
           data.push(rowData);
       });

       const worksheet = XLSX.utils.aoa_to_sheet(data);
       const workbook = XLSX.utils.book_new();
       XLSX.utils.book_append_sheet(workbook, worksheet, config.sheetName);
       const csvContent = "\uFEFF" + XLSX.write(workbook, {
           bookType: "csv",
           type: "string",
           FS: "|"
       });

       const blob = new Blob([csvContent], { type: "text/csv;charset=utf-8;" });
       const link = Object.assign(document.createElement("a"), {
           href: URL.createObjectURL(blob),
           download: config.fileName
       });
       document.body.appendChild(link);
       link.click();
       document.body.removeChild(link);
   });


});
