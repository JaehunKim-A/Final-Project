document.addEventListener("DOMContentLoaded", function () {
    const entity = document.body.dataset.entity || "unknown";
    const label = document.body.dataset.label || "항목";
    const fields = JSON.parse(document.body.dataset.fields || "[]");
    const accordionFields = JSON.parse(document.body.dataset.accordionFields || "[]");
    const table = document.querySelector("#table1");

    const primaryKey = {
        customer: "id",
        customerOrders: "orderId",
        rawMaterialSupplier: "id"
    }[entity] || "id";

    const fieldMap = {
        customer: {
            id: "customerId",
            name: "customerName",
            contact: "contactInfo",
            address: "address",
            reg: "regDate",
            mod: "modDate"
        },
        customerOrders: {
            id: "orderId",
            customerId: "customerId",
            customerName: "customerName",
            orderDate: "orderDate",
            totalAmount: "totalAmount",
            status: "status",
            reg: "regDate",
            mod: "modDate"
        },
        rawMaterialSupplier: {
            id: "id",
            name: "supplierName",
            contact: "contactInfo",
            address: "address",
            email: "email",
            phone: "phoneNumber",
            reg: "regDate",
            mod: "modDate"
        }
    };

    const utils = {
        capitalize: str => str.charAt(0).toUpperCase() + str.slice(1),
        getIdElement: prefix => document.getElementById(`${prefix}${utils.capitalize(entity)}Id`),
        getFormElement: prefix => document.getElementById(`${prefix}${utils.capitalize(entity)}Form`),
        setInputValue: (id, value) => {
            const el = document.getElementById(id);
            if (el) el.value = value;
        },
        renderDataAttrs: (data, fields) =>
            fields.map(f => {
                const dtoField = fieldMap[entity][f] || f;
                return `data-${dtoField.toLowerCase()}="${data[dtoField] || ''}"`;
            }).join(" ")
    };

    let dataTable = null;

    function initDataTable() {
        if (dataTable) {
            dataTable.destroy();
            dataTable = null;
        }

        dataTable = new simpleDatatables.DataTable(table, {
            perPage: 10,
            perPageSelect: [5, 10, 15, 20, 999],
            searchable: false,
            sortable: true,
            labels: {
                placeholder: `검색`,
                perPage: `{select}개씩 보기`,
                noRows: `${label}을 찾을 수 없습니다.`,
                info: `현재 페이지 {start}~{end} / 전체:{rows}`,
                noResults: "검색 결과가 없습니다.",
                perPageSelect: ["5개", "10개", "15개", "20개", "전체"]
            }
        });


        dataTable.on("datatable.init", () => {
            refreshPagination();
            setRowClickEvents();

            document.querySelectorAll("select.dataTable-selector option").forEach(option => {
                        if (option.value === "999") option.textContent = "전체";
            });
        });

        dataTable.on("datatable.perpage", refreshPagination);
        dataTable.on("datatable.update", refreshPagination);
        dataTable.on("datatable.sort", refreshPagination);
        dataTable.on("datatable.page", refreshPagination);
    }


    function refreshPagination() {
        const paginations = dataTable.wrapper.querySelectorAll("ul.dataTable-pagination-list");

        const isAllSelected = dataTable.options.perPage === 999;
        const hasActiveFilter = Array.from(document.querySelectorAll(".column-search"))
            .some(input => input.value.trim() !== "");

        if (isAllSelected && hasActiveFilter) {
            paginations.forEach(p => p.parentElement.style.display = "none"); // pagination 영역 숨김
        } else {
            paginations.forEach(p => {
                p.parentElement.style.display = ""; // 다시 보이게
                p.classList.add("pagination", "pagination-primary");
            });
            dataTable.wrapper.querySelectorAll("ul.dataTable-pagination-list li").forEach(li =>
                li.classList.add("page-item")
            );
            dataTable.wrapper.querySelectorAll("ul.dataTable-pagination-list li a").forEach(link =>
                link.classList.add("page-link")
            );
        }
    }


    function setRowClickEvents() {
        table.removeEventListener("click", rowClickHandler);
        table.addEventListener("click", rowClickHandler);

        document.querySelectorAll(".accordion-toggle").forEach(btn => {
            btn.addEventListener("click", function () {
                const row = this.closest("tr");
                const id = row.querySelector(`.${entity}-id`)?.textContent?.trim();
                const address = row.querySelector(`.${entity}-address`)?.textContent?.trim();

                if (id && address) {
                    const mapId = `map-${id}`;
                    loadDynamicMap(address, mapId);
                }
            });
        });
    }

    function rowClickHandler(e) {
        const editBtn = e.target.closest(".btn-edit");
        if (editBtn) {
            let id = editBtn.dataset.id;
            if (!id || id === "undefined") {
                const row = editBtn.closest("tr");
                const pkCell = row.querySelector(`.${entity}-${primaryKey}`) || row.querySelector(`.${primaryKey}`);
                id = pkCell?.innerText?.trim() || "";
            }
            utils.setInputValue(`edit${utils.capitalize(entity)}Id`, id);
            fields.forEach(field => {
                const dtoField = fieldMap[entity][field] || field;
                utils.setInputValue(`edit${utils.capitalize(field)}`, editBtn.dataset[dtoField.toLowerCase()] || "");
            });
            utils.setInputValue("editRegDate", editBtn.dataset[fieldMap[entity].reg?.toLowerCase()] || "");
            utils.setInputValue("editModDate", editBtn.dataset[fieldMap[entity].mod?.toLowerCase()] || "");
            return;
        }

        const deleteBtn = e.target.closest(".btn-delete");
        if (deleteBtn) {
            const id = deleteBtn.dataset.id;
            const name = deleteBtn.dataset.name;
            utils.setInputValue(`delete${utils.capitalize(entity)}Id`, id);
            const msg = document.getElementById("deleteConfirmMessage");
            if (msg) msg.textContent = `정말로 ${name || id}을 삭제하시겠습니까?`;
            return;
        }

        const detailCell = e.target.closest(`.${entity}-detail-toggle`);
        if (detailCell) toggleAccordionRow(detailCell.closest("tr"));
    }

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
            if (fieldMap[entity][f]) data[fieldMap[entity][f]] = data[f];
        });

        const rowIndex = row.rowIndex;
        const accordionRow = document.createElement("tr");
        accordionRow.classList.add("accordion-row");
        accordionRow.innerHTML = renderAccordionRow(data, rowIndex);
        row.parentNode.insertBefore(accordionRow, row.nextSibling);
        new bootstrap.Collapse(document.getElementById(`collapse-${rowIndex}`), { toggle: true });

        loadDynamicMap(data[fieldMap[entity].address], `map-${data[fieldMap[entity].id]}`);
    }

    function renderAccordionRow(data, index) {
        const accordionLabels = JSON.parse(document.body.dataset.accordionLabels || "[]");
        return `
        <td colspan="99">
            <div class="accordion" id="accordionDetail-${index}">
                <div class="accordion-item">
                    <div id="collapse-${index}" class="accordion-collapse collapse">
                        <div class="accordion-body p-0">
                            <table class="table table-bordered mb-0 align-middle">
                                <thead>
                                    <tr>
                                        ${accordionFields.map((f, i) => `<th>${accordionLabels[i] || f}</th>`).join("")}
                                        <th>수정/삭제</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        ${accordionFields.map(f => `<td>${data[f]}</td>`).join("")}
                                        <td>
                                            <button class="btn btn-sm btn-outline-primary btn-edit"
                                                data-bs-toggle="modal" data-bs-target="#editModal"
                                                data-id="${data[fieldMap[entity].id]}"
                                                ${utils.renderDataAttrs(data, fields)}
                                                data-${fieldMap[entity].reg?.toLowerCase()}="${data[fieldMap[entity].reg] || ''}"
                                                data-${fieldMap[entity].mod?.toLowerCase()}="${data[fieldMap[entity].mod] || ''}">수정</button>
                                            <button class="btn btn-sm btn-outline-info btn-delete"
                                                data-bs-toggle="modal" data-bs-target="#deleteModal"
                                                data-id="${data[fieldMap[entity].id]}" data-name="${data[fieldMap[entity].name] || ''}">삭제</button>
                                        </td>
                                    </tr>
                                </tbody>
                                <tr>
                                  <td colspan="${accordionFields.length + 1}">
                                    <div class="map-container" id="map-${data[fieldMap[entity].id]}" style="height: 300px;"></div>
                                  </td>
                                </tr>
                            </table>

                        </div>
                    </div>
                </div>
            </div>
        </td>`;
    }

    async function filterAndRender() {
        try {
            const res = await fetch(`/api/${entity}/all`);
            const allData = await res.json();

            const filterValues = {};
            document.querySelectorAll(".column-search").forEach(input => {
                filterValues[input.dataset.column] = input.value;
            });

            // customerId → customerName 매핑
            const customerMap = {};
            document.querySelectorAll("#customerId option, #editCustomerId option").forEach(opt => {
                if (opt.value) {
                    const text = opt.textContent;
                    const id = opt.value;
                    const name = text.split(" (고객코드:")[0];
                    customerMap[id] = name;
                }
            });

            const filteredData = allData.filter(item => {
                return Object.entries(filterValues)
                    .filter(([_, value]) => value.trim() !== "")
                    .every(([key, value]) => {
                        const keyword = value.toLowerCase();

                        if (entity === "customerOrders") {
                            if (key === "customerName") {
                                const name = customerMap[item.customerId] || "";
                                return name.toLowerCase().includes(keyword);
                            }
                        }

                        const dtoField = fieldMap[entity]?.[key] || key;
                        const fieldValue = (item[dtoField] || "").toString().toLowerCase();

                        return fieldValue.includes(keyword);
                    });
            });

            if (dataTable) {
                dataTable.destroy();
                dataTable = null;
            }

            const tbody = table.querySelector("tbody");
            tbody.innerHTML = "";

            filteredData.forEach(item => {
                const tr = document.createElement("tr");
                tr.classList.add("data-row");

                if (entity === "customer") {
                    tr.innerHTML = `
                        <td class="${entity}-id ${entity}-detail-toggle" style="cursor:pointer;">${item.customerId}</td>
                        <td class="${entity}-name ${entity}-detail-toggle" style="cursor:pointer;">${item.customerName}</td>
                        <td class="${entity}-contact ${entity}-detail-toggle" style="cursor:pointer;">${item.contactInfo}</td>
                        <td class="${entity}-address ${entity}-detail-toggle" style="cursor:pointer;">${item.address}</td>
                        <td class="${entity}-reg ${entity}-detail-toggle" style="cursor:pointer;">${item.regDateFormatted}</td>
                        <td class="${entity}-mod ${entity}-detail-toggle" style="cursor:pointer;">${item.modDateFormatted}</td>
                    `;
                } else if (entity === "customerOrders") {
                    const customerName = customerMap[item.customerId] || "미지정";
                    tr.innerHTML = `
                        <td class="${entity}-orderId ${entity}-detail-toggle" style="cursor:pointer;">${item.orderId}</td>
                        <td class="${entity}-customerId ${entity}-detail-toggle" style="cursor:pointer;">${item.customerId}</td>
                        <td class="${entity}-customerName ${entity}-detail-toggle" style="cursor:pointer;">${customerName}</td>
                        <td class="${entity}-orderDate ${entity}-detail-toggle" style="cursor:pointer;">${item.orderDateFormatted}</td>
                        <td class="${entity}-status ${entity}-detail-toggle" style="cursor:pointer;">${item.status}</td>
                        <td class="${entity}-totalAmount ${entity}-detail-toggle" style="cursor:pointer;">${item.totalAmount}</td>
                        <td class="${entity}-reg ${entity}-detail-toggle" style="cursor:pointer;">${item.regDateFormatted}</td>
                        <td class="${entity}-mod ${entity}-detail-toggle" style="cursor:pointer;">${item.modDateFormatted}</td>
                    `;
                } else if (entity === "rawMaterialSupplier") {
                    tr.innerHTML = `
                        <td class="${entity}-id ${entity}-detail-toggle" style="cursor:pointer;">${item.supplierId}</td>
                        <td class="${entity}-name ${entity}-detail-toggle" style="cursor:pointer;">${item.supplierName}</td>
                        <td class="${entity}-contact ${entity}-detail-toggle" style="cursor:pointer;">${item.contactInfo}</td>
                        <td class="${entity}-address ${entity}-detail-toggle" style="cursor:pointer;">${item.address}</td>
                        <td class="${entity}-email ${entity}-detail-toggle" style="cursor:pointer;">${item.email}</td>
                        <td class="${entity}-phone ${entity}-detail-toggle" style="cursor:pointer;">${item.phoneNumber}</td>
                        <td class="${entity}-reg ${entity}-detail-toggle" style="cursor:pointer;">${item.regDateFormatted}</td>
                        <td class="${entity}-mod ${entity}-detail-toggle" style="cursor:pointer;">${item.modDateFormatted}</td>
                    `;
                }

                tbody.appendChild(tr);
            });

            initDataTable();

            document.querySelectorAll(".column-search").forEach(input => {
                const col = input.dataset.column;
                input.value = filterValues[col] || "";
            });

            setupColumnSearch();

        } catch (err) {
            console.error("❌ filterAndRender 오류:", err);
        }
    }

    function setupColumnSearch() {
        const searchInputs = document.querySelectorAll(".column-search");
        searchInputs.forEach(input => {
            input.addEventListener("keydown", (e) => {
                if (e.key === "Enter") {
                    e.preventDefault(); // 폼 제출 방지
                    filterAndRender();
                }
            });
        });
    }

    // Excel 다운로드 (.xlsx)
    document.getElementById("downloadVisibleExcel")?.addEventListener("click", () => {
        const visibleRows = table.querySelectorAll("tbody tr:not(.accordion-row)");

        const exportConfig = {
            customer: {
                headers: ["코드", "이름", "연락처", "주소", "등록일", "수정일"],
                classes: ["customer-id", "customer-name", "customer-contact", "customer-address", "customer-reg", "customer-mod"],
                sheetName: "고객 목록",
                fileName: "고객_검색결과.xlsx"
            },
            rawMaterialSupplier: {
                headers: ["코드", "이름", "연락처", "주소", "이메일", "전화번호", "등록일", "수정일"],
                classes: ["rawMaterialSupplier-id", "rawMaterialSupplier-name", "rawMaterialSupplier-contact", "rawMaterialSupplier-address", "rawMaterialSupplier-email", "rawMaterialSupplier-phone", "rawMaterialSupplier-reg", "rawMaterialSupplier-mod"],
                sheetName: "공급업체 목록",
                fileName: "공급업체_검색결과.xlsx"
            },
            customerOrders: {
                headers: ["주문 ID", "고객 ID", "고객명", "주문일", "수량", "상태", "등록일", "수정일"],
                classes: ["customerOrders-orderId", "customerOrders-customerId", "customerOrders-customerName", "customerOrders-orderDate", "customerOrders-totalAmount", "customerOrders-status", "customerOrders-reg", "customerOrders-mod"],
                sheetName: "주문 목록",
                fileName: "주문_검색결과.xlsx"
            }
        };

        const config = exportConfig[entity];
        if (!config) return alert(`${label} Excel 다운로드 구성이 없습니다.`);

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

    // CSV 다운로드 (.csv)
    document.getElementById("downloadVisibleCsv")?.addEventListener("click", () => {
        const visibleRows = table.querySelectorAll("tbody tr:not(.accordion-row)");

        const exportConfig = {
            customer: {
                headers: ["코드", "이름", "연락처", "주소", "등록일", "수정일"],
                classes: ["customer-id", "customer-name", "customer-contact", "customer-address", "customer-reg", "customer-mod"],
                fileName: "고객_검색결과.csv"
            },
            rawMaterialSupplier: {
                headers: ["코드", "이름", "연락처", "주소", "이메일", "전화번호", "등록일", "수정일"],
                classes: ["rawMaterialSupplier-id", "rawMaterialSupplier-name", "rawMaterialSupplier-contact", "rawMaterialSupplier-address", "rawMaterialSupplier-email", "rawMaterialSupplier-phone", "rawMaterialSupplier-reg", "rawMaterialSupplier-mod"],
                fileName: "공급업체_검색결과.csv"
            },
            customerOrders: {
                headers: ["주문 ID", "고객 ID", "고객명", "주문일", "수량", "상태", "등록일", "수정일"],
                classes: ["customerOrders-orderId", "customerOrders-customerId", "customerOrders-customerName", "customerOrders-orderDate", "customerOrders-totalAmount", "customerOrders-status", "customerOrders-reg", "customerOrders-mod"],
                fileName: "주문_검색결과.csv"
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

        const csvString = data.map(row => row.join("|")).join("\r\n");
        const blob = new Blob(["\uFEFF" + csvString], { type: "text/csv;charset=utf-8;" });
        const link = Object.assign(document.createElement("a"), {
            href: URL.createObjectURL(blob),
            download: config.fileName
        });
        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
    });

    document.getElementById("csvUploadForm")?.addEventListener("submit", async function (e) {
        e.preventDefault();

        const form = e.target;
        const formData = new FormData(form);

        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        try {
            const res = await fetch(form.action, {
                method: "POST",
                body: formData,
                headers: {
                    [header]: token
                }
            });

            if (!res.ok) {
                const text = await res.text();
                throw new Error(`업로드 실패: ${text}`);
            }

            alert("CSV 업로드 성공!");
            location.reload();
        } catch (err) {
            console.error("❌ CSV 업로드 실패:", err);
            alert("CSV 업로드 중 오류 발생!");
        }
    });

    // customer 등록
    document.getElementById("registerCustomerForm")?.addEventListener("submit", function (e) {
        e.preventDefault();

        const form = e.target;
        const token = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

        const json = {
                customerName: form.customerName.value,
                contactInfo: form.contactInfo.value,
                address: form.address.value
            };

            fetch("/api/customer", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                    [header]: token
                },
                body: JSON.stringify(json)
            }).then(res => {
                if (!res.ok) throw new Error("등록 실패");
                alert("고객 등록 완료");
                location.reload();
            }).catch(err => {
                alert("고객 등록 실패");
                console.error(err);
        });
    });

    // customer 수정
    document.getElementById("editCustomerForm")?.addEventListener("submit", function (e) {
        e.preventDefault();

        const form = e.target;
        const id = document.getElementById("editCustomerId")?.value;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const json = {
            customerName: form.customerName.value,
            contactInfo: form.contactInfo.value,
            address: form.address.value
        };

        fetch(`/api/customer/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify(json)
        }).then(res => {
            if (!res.ok) throw new Error("수정 실패");
            alert("고객 수정 완료");
            location.reload();
        }).catch(err => {
            alert("고객 수정 실패");
            console.error(err);
        });
    });

    // customer 삭제
    document.getElementById("deleteCustomerForm")?.addEventListener("submit", function (e) {
        e.preventDefault();

        const id = document.getElementById("deleteCustomerId")?.value;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        fetch(`/api/customer/${id}`, {
            method: "DELETE",
            headers: { [header]: token }
        }).then(res => {
            if (!res.ok) throw new Error("삭제 실패");
            alert("고객 삭제 완료");
            location.reload();
        }).catch(err => {
            alert("고객 삭제 실패");
            console.error(err);
        });
    });

    // customerOrder 등록
    document.getElementById("registerCustomerOrdersForm")?.addEventListener("submit", function (e) {
        e.preventDefault();

        const form = e.target;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const json = {
            customerId: form.customerId.value,
            orderDate: form.orderDate.value,
            totalAmount: form.totalAmount.value,
            status: form.status.value
        };

        fetch("/api/customerOrders", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify(json)
        }).then(res => {
            if (!res.ok) throw new Error("등록 실패");
            alert("주문 등록 완료");
            location.reload();
        }).catch(err => {
            alert("주문 등록 실패");
            console.error(err);
        });
    });

    // customerOrder 수정
    document.getElementById("editCustomerOrdersForm")?.addEventListener("submit", function (e) {
        e.preventDefault();

        const form = e.target;
        const id = document.getElementById("editCustomerOrdersId")?.value;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const json = {
            customerId: form.customerId.value,
            orderDate: form.orderDate.value,
            totalAmount: form.totalAmount.value,
            status: form.status.value
        };

        fetch(`/api/customerOrders/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify(json)
        }).then(res => {
            if (!res.ok) throw new Error("수정 실패");
            alert("주문 수정 완료");
            location.reload();
        }).catch(err => {
            alert("주문 수정 실패");
            console.error(err);
        });
    });

    // customerOrder 삭제
    document.getElementById("deleteCustomerOrdersForm")?.addEventListener("submit", function (e) {
        e.preventDefault();

        const id = document.getElementById("deleteCustomerOrdersId")?.value;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        fetch(`/api/customerOrders/${id}`, {
            method: "DELETE",
            headers: { [header]: token }
        }).then(res => {
            if (!res.ok) throw new Error("삭제 실패");
            alert("주문 삭제 완료");
            location.reload();
        }).catch(err => {
            alert("주문 삭제 실패");
            console.error(err);
        });
    });

    // rawMaterialSupplier 등록
    document.getElementById("registerRawMaterialSupplierForm")?.addEventListener("submit", function (e) {
        e.preventDefault();
        const form = e.target;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const json = {
            supplierName: form.supplierName.value,
            contactInfo: form.contactInfo.value,
            address: form.address.value,
            email: form.email.value,
            phoneNumber: form.phoneNumber.value
        };

        fetch("/api/rawMaterialSupplier", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify(json)
        }).then(res => {
            if (!res.ok) throw new Error("등록 실패");
            alert("공급자 등록 완료");
            location.reload();
        }).catch(err => {
            alert("공급자 등록 실패");
            console.error(err);
        });
    });

    // rawMaterialSupplier 수정
    document.getElementById("editRawMaterialSupplierForm")?.addEventListener("submit", function (e) {
        e.preventDefault();
        const id = document.getElementById("editRawMaterialSupplierId")?.value;
        const form = e.target;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        const json = {
            supplierName: form.supplierName.value,
            contactInfo: form.contactInfo.value,
            address: form.address.value,
            email: form.email.value,
            phoneNumber: form.phoneNumber.value
        };

        fetch(`/api/rawMaterialSupplier/${id}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                [header]: token
            },
            body: JSON.stringify(json)
        }).then(res => {
            if (!res.ok) throw new Error("수정 실패");
            alert("공급자 수정 완료");
            location.reload();
        }).catch(err => {
            alert("공급자 수정 실패");
            console.error(err);
        });
    });

    // rawMaterialSupplier 삭제
    document.getElementById("deleteRawMaterialSupplierForm")?.addEventListener("submit", function (e) {
        e.preventDefault();
        const id = document.getElementById("deleteRawMaterialSupplierId")?.value;
        const token = document.querySelector('meta[name="_csrf"]').getAttribute('content');
        const header = document.querySelector('meta[name="_csrf_header"]').getAttribute('content');

        fetch(`/api/rawMaterialSupplier/${id}`, {
            method: "DELETE",
            headers: { [header]: token }
        }).then(res => {
            if (!res.ok) throw new Error("삭제 실패");
            alert("공급자 삭제 완료");
            location.reload();
        }).catch(err => {
            alert("공급자 삭제 실패");
            console.error(err);
        });
    });

    async function fetchLatLngByAddress(address) {
        const response = await fetch(`/api/geocode?query=${encodeURIComponent(address)}`);
        const data = await response.json();

        console.log("🛰️ Geocode 응답 구조 확인:", data);

        if (data.addresses?.length > 0) {
            const { x, y } = data.addresses[0];
            return { lat: parseFloat(y), lng: parseFloat(x) };
        } else {
            throw new Error("주소를 찾을 수 없습니다.");
        }
    }



    async function loadDynamicMap(address, mapId) {
        if (!address) return;

        try {
            const { lat, lng } = await fetchLatLngByAddress(address);

            const map = new naver.maps.Map(mapId, {
                center: new naver.maps.LatLng(lat, lng),
                zoom: 15
            });

            new naver.maps.Marker({
                map,
                position: new naver.maps.LatLng(lat, lng)
            });
        } catch (err) {
            console.warn("🛑 지도 마커 표시 실패:", err);
        }
    }


    // 초기 실행
    setupColumnSearch();
    filterAndRender();
});