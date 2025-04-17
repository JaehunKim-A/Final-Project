const MY_API_KEY = "";
document.addEventListener('DOMContentLoaded', function() {
    // Get references to elements
    const chatButton = document.querySelector('.chat-bot');
    const chatModal = document.getElementById('chatModal');
    const modalDialog = chatModal.querySelector('.modal-dialog');

    // Add click event to the chat button
    chatButton.addEventListener('click', function(e) {
        e.preventDefault();

        // Show the modal programmatically
        const modal = new bootstrap.Modal(chatModal);
        modal.show();

        // Initial modal position with no transform
        fixModalPosition();
    });

    // Add event listener for when modal is shown
    chatModal.addEventListener('shown.bs.modal', function() {
        fixModalPosition();

        // jQuery를 사용할 수 있다면 드래그 가능하게 설정
        if (typeof jQuery !== 'undefined' && typeof jQuery.ui !== 'undefined') {
            initDraggable();
        } else {
            // jQuery UI가 없는 경우 기본 JavaScript로 드래그 기능 구현
            initVanillaDrag();
        }
    });

    // Function to fix modal position
    function fixModalPosition() {
        // Apply fixed positioning
        modalDialog.style.position = 'fixed';
        modalDialog.style.bottom = '90px';
        modalDialog.style.right = '20px';
        modalDialog.style.transform = 'none';
        modalDialog.style.margin = '0';

        // Force modal to be visible
        chatModal.style.paddingRight = '0 !important';
    }

    // jQuery UI를 사용한 드래그 기능 초기화
    function initDraggable() {
        $(modalDialog).draggable({
            handle: '.card-header', // 카드 헤더를 드래그 핸들로 사용
            containment: 'window',  // 윈도우 내에서만 드래그 가능하도록 제한
            scroll: false
        });
    }

    // 바닐라 JavaScript로 드래그 기능 구현
    function initVanillaDrag() {
        const header = chatModal.querySelector('.card-header');

        let isDragging = false;
        let offsetX, offsetY;

        // 드래그 시작
        header.addEventListener('mousedown', function(e) {
            isDragging = true;

            // 클릭한 위치와 모달 위치의 차이 계산
            const rect = modalDialog.getBoundingClientRect();
            offsetX = e.clientX - rect.left;
            offsetY = e.clientY - rect.top;

            // 사용자 선택 방지
            modalDialog.style.userSelect = 'none';
        });

        // 드래그 중
        document.addEventListener('mousemove', function(e) {
            if (!isDragging) return;

            // 새 위치 계산 및 설정
            const newX = e.clientX - offsetX;
            const newY = e.clientY - offsetY;

            // 윈도우 경계 확인
            const maxX = window.innerWidth - modalDialog.offsetWidth;
            const maxY = window.innerHeight - modalDialog.offsetHeight;

            // 경계 내에서만 이동 허용
            const constX = Math.max(0, Math.min(newX, maxX));
            const constY = Math.max(0, Math.min(newY, maxY));

            modalDialog.style.left = constX + 'px';
            modalDialog.style.top = constY + 'px';
            modalDialog.style.right = 'auto';
            modalDialog.style.bottom = 'auto';

            e.preventDefault();
        });

        // 드래그 종료
        document.addEventListener('mouseup', function() {
            isDragging = false;
            modalDialog.style.userSelect = '';
        });

        // 창 밖으로 나가는 경우 드래그 종료
        document.addEventListener('mouseleave', function() {
            isDragging = false;
        });
    }

    // Fix issue with modal backdrop
    chatModal.addEventListener('hidden.bs.modal', function() {
        document.body.classList.remove('modal-open');
        const backdrops = document.querySelectorAll('.modal-backdrop');
        backdrops.forEach(backdrop => {
            backdrop.parentNode.removeChild(backdrop);
        });
    });
});

// ---------- Gemini REST API 채팅 구현 ----------

// API 키와 엔드포인트 설정
const API_KEY = MY_API_KEY; // 여기에 실제 API 키 입력
const API_ENDPOINT = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";

// 초기 프롬프트 설정
const INITIAL_PROMPT = `너가 가진 정보는
{
사이드바 메뉴 이름 : Raw Material 밑 Raw Materials, http://localhost:9094/rawMaterial/rawMaterial : 원자재를 등록할 수 있는 페이지
사이드바 메뉴 이름 : Raw Material 밑 Stock, http://localhost:9094/rawMaterial/stock : 원자재 재고를 확인하는 페이지
사이드바 메뉴 이름 : Raw Material 밑 Inbound, http://localhost:9094/raw-material/inbound/list : 원자재 입고 리스트 ( CRUD를 지원하며 원자재 입고 확인)
사이드바 메뉴 이름 : Raw Material 밑 Outbound, http://localhost:9094/raw-material/outbound/list : 원자재 출고 리스트 ( CRUD를 지원하며 원자재 출고 확인)
사이드바 메뉴 이름 : Finished Product 밑 Finished Products, http://localhost:9094/finishedProduct/finishedProduct : 완제품을 등록할 수 있는 페이지
사이드바 메뉴 이름 : Finished Product 밑 Stock, http://localhost:9094/finishedProduct/stock : 완제품 재고를 확인하는 페이지
사이드바 메뉴 이름 : Finished Product 밑 Inbound, http://localhost:9094/finished-product/inbound/list : 완제품 입고 리스트 ( CRUD를 지원하며 완제품 입고 확인)
사이드바 메뉴 이름 : Finished Product 밑 Outbound, http://localhost:9094/finished-product/outbound/list : 완제품 출고 리스트 ( CRUD를 지원하며 완제품 출고 확인)
사이드바 메뉴 이름 : Code Management, http://localhost:9094/codeManagement/codeManagement : 원자재와 완제품의 포장 정보를 입력하는 페이지
사이드바 메뉴 이름 : Employee 밑 Employees, http://localhost:9094/table/employee : 사원 정보 등록, 수정, 삭제 페이지
사이드바 메뉴 이름 : Customer 밑 Customers, http://localhost:9094/table/customer : 고객 정보 등록, 수정, 삭제 페이지
사이드바 메뉴 이름 : Customer 밑 CustomerOrders, http://localhost:9094/table/customerOrders : 주문을 등록, 수정, 삭제 페이지
사이드바 메뉴 이름 : Supplier 밑 Suppliers, http://localhost:9094/table/rawMaterialSupplier : 거래처 등록, 수정, 삭제 페이지
사이드바 메뉴 이름 : Factory 밑 Machine, http://localhost:9094/productProcessManagement/productProcessManagement :  공장의 머신 이미지 배치 시뮬, 공장 기계별, 원자재 정보 등 시각화 차트(라인, 바, 스캐터, 도넛, 히트맵), 머신별 히스토리 제공하는 페이지
}
이렇게 가지고 있고 너는 이것을 물어봤을 때 도움을 주는 어시스턴트 봇 Alfy야.
여기 적힌 정보의외의 질문이 들어왔을때는 간단하게만 대답해주고 이 정보에 대해서만 되도록이면 성의것 대답해줘.
그리고 너에게 채팅을 하는 사람은 주인님이야.
`;


// 채팅 기록 저장
let chatHistory = [];

// 채팅 메시지 추가 함수
function addChatMessage(message, isUser = false) {
    const chatContent = document.querySelector('.chat-content');

    // 채팅 메시지 HTML 생성 (isUser가 true면 오른쪽, false면 왼쪽)
    const chatHtml = `
        <div class="chat ${isUser ? '' : 'chat-left'}">
            <div class="chat-body">
                <div class="chat-message">${message}</div>
            </div>
        </div>
    `;

    // 채팅 컨테이너에 메시지 추가
    chatContent.insertAdjacentHTML('beforeend', chatHtml);

    // 스크롤을 맨 아래로 이동
    scrollToBottom();
}

// 스크롤을 맨 아래로 이동하는 함수
function scrollToBottom() {
    const chatBody = document.querySelector('.chat-box-card .card-body');
    chatBody.scrollTop = chatBody.scrollHeight;
}

// Gemini API를 호출하여 응답 얻기
async function getGeminiResponse(userMessage) {
    try {
        // 사용자 메시지를 채팅 기록에 추가
        chatHistory.push({ role: "user", content: userMessage });

        // API 요청용 메시지 준비
        let fullPrompt = INITIAL_PROMPT + "\n\n";

        // 이전 대화 내용을 포함 (최대 5개 메시지)
        const recentMessages = chatHistory.slice(-5);
        for (const msg of recentMessages) {
            if (msg.role === "user") {
                fullPrompt += "사용자: " + msg.content + "\n";
            } else if (msg.role === "assistant") {
                fullPrompt += "Alfy: " + msg.content + "\n";
            }
        }

        // API 요청 데이터 준비
        const requestData = {
            contents: [{
                parts: [{ text: fullPrompt }]
            }]
        };

        // API 요청 보내기
        const response = await fetch(`${API_ENDPOINT}?key=${API_KEY}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(requestData)
        });

        // 응답 확인
        if (!response.ok) {
            const errorData = await response.json();
            console.error("API 오류:", errorData);
            throw new Error(`API 요청 실패: ${response.status} ${response.statusText}`);
        }

        // 응답 데이터 파싱
        const responseData = await response.json();

        // 응답 텍스트 추출
        const responseText = responseData.candidates[0].content.parts[0].text.trim();

        // AI 응답을 채팅 기록에 추가
        chatHistory.push({ role: "assistant", content: responseText });

        return responseText;
    } catch (error) {
        console.error("Gemini 응답 오류:", error);
        return "죄송합니다, 응답 처리 중 오류가 발생했습니다. 다시 시도해 주세요.";
    }
}

// DOM이 로드된 후 실행
document.addEventListener('DOMContentLoaded', function() {
    // 채팅 입력란과 메시지 전송 이벤트 설정
    const chatInput = document.querySelector('.message-form input');

    // 기존 전송 버튼 찾기 (HTML에 이미 존재함)
    const sendButton = document.querySelector('.send-button');

    // 메시지 전송 함수
    const sendMessage = async () => {
        const message = chatInput.value.trim();

        // 메시지가 비어있으면 무시
        if (!message) return;

        // 사용자 메시지 표시 (오른쪽)
        addChatMessage(message, true);

        // 입력란 비우기
        chatInput.value = '';

        // 로딩 표시 (왼쪽)
        const loadingMessage = "응답을 생성하는 중...";
        const loadingElement = document.createElement('div');
        loadingElement.className = 'chat chat-left';
        loadingElement.innerHTML = `
            <div class="chat-body">
                <div class="chat-message typing-indicator">${loadingMessage}</div>
            </div>
        `;
        document.querySelector('.chat-content').appendChild(loadingElement);

        try {
            // Gemini에서 응답 받기
            const response = await getGeminiResponse(message);

            // 로딩 메시지 제거
            document.querySelector('.chat-content').removeChild(loadingElement);

            // AI 응답 표시 (왼쪽)
            addChatMessage(response, false);
        } catch (error) {
            // 로딩 메시지 제거
            document.querySelector('.chat-content').removeChild(loadingElement);

            // 오류 메시지 표시 (왼쪽)
            addChatMessage("죄송합니다, 오류가 발생했습니다. 다시 시도해 주세요.", false);
            console.error('응답 오류:', error);
        }
    };

    // 엔터 키 이벤트
    chatInput.addEventListener('keypress', function(e) {
        if (e.key === 'Enter') {
            e.preventDefault();
            sendMessage();
        }
    });

    // 전송 버튼 클릭 이벤트
    if (sendButton) {
        sendButton.addEventListener('click', sendMessage);
    }

    // 채팅 모달이 표시될 때 입력란에 포커스
    const chatModal = document.getElementById('chatModal');
    chatModal.addEventListener('shown.bs.modal', function() {
        chatInput.focus();
    });
});

// ----------- sidebar
// 사이드바 활성화 스크립트 - layout.js 파일에 추가

/**
 * 사이드바 메뉴 활성화 스크립트
 * 현재 페이지 URL에 따라 해당하는 사이드바 메뉴에 active 클래스를 추가합니다.
 */
/**
 * 사이드바 메뉴 활성화 스크립트
 * 현재 페이지 URL에 따라 하나의 메뉴 항목에만 active 클래스를 추가합니다.
 */
document.addEventListener('DOMContentLoaded', function() {
    // 현재 페이지 URL 가져오기
    const currentPath = window.location.pathname;
    console.log('현재 경로:', currentPath);

    // 모든 active 클래스 제거 함수
    function removeAllActiveClasses() {
        // 모든 sidebar-item에서 active 클래스 제거
        document.querySelectorAll('.sidebar-item').forEach(item => {
            item.classList.remove('active');
        });

        // 모든 submenu-item에서 active 클래스 제거
        document.querySelectorAll('.submenu-item').forEach(item => {
            item.classList.remove('active');
        });
    }

    // submenu 항목 클릭 시 active 클래스 관리
    const submenuLinks = document.querySelectorAll('.submenu-item a.submenu-link');
    submenuLinks.forEach(link => {
        link.addEventListener('click', function() {
            // 먼저 모든 active 클래스 제거
            removeAllActiveClasses();

            // 클릭된 링크의 부모 sidebar-item에만 active 클래스 추가
            const parentSidebarItem = this.closest('.sidebar-item');
            if (parentSidebarItem) {
                parentSidebarItem.classList.add('active');
            }

            // 클릭된 submenu-item에만 active 클래스 추가
            const submenuItem = this.closest('.submenu-item');
            if (submenuItem) {
                submenuItem.classList.add('active');
            }

            // 클릭된 링크 정보 저장
            sessionStorage.setItem('activeLink', this.getAttribute('href'));
        });
    });

    // 일반 메뉴 항목(하위 메뉴 없는 항목) 클릭 관리
    const singleMenuLinks = document.querySelectorAll('.sidebar-item:not(.has-sub) > .sidebar-link');
    singleMenuLinks.forEach(link => {
        link.addEventListener('click', function() {
            // 모든 active 클래스 제거
            removeAllActiveClasses();

            // 현재 항목에만 active 클래스 추가
            const parentItem = this.parentElement;
            if (parentItem) {
                parentItem.classList.add('active');
            }

            // 클릭된 링크 정보 저장
            sessionStorage.setItem('activeLink', this.getAttribute('href'));
        });
    });

    // 페이지 로드 시 현재 URL에 맞는 메뉴 항목 활성화
    function activateMenuByUrl() {
        // 먼저 모든 active 클래스 제거
        removeAllActiveClasses();

        let foundMatch = false;

        // 1. 정확한 URL 일치 확인 (submenu 항목)
        submenuLinks.forEach(link => {
            const href = link.getAttribute('href');
            if (href === currentPath) {
                // submenu-item에 active 클래스 추가
                const submenuItem = link.closest('.submenu-item');
                if (submenuItem) {
                    submenuItem.classList.add('active');
                }

                // 부모 sidebar-item에 active 클래스 추가
                const sidebarItem = link.closest('.sidebar-item');
                if (sidebarItem) {
                    sidebarItem.classList.add('active');
                }

                // submenu 표시
                const submenu = sidebarItem.querySelector('.submenu');
                if (submenu) {
                    submenu.style.display = 'block';
                }

                foundMatch = true;
            }
        });

        // 2. 정확한 URL 일치 확인 (일반 메뉴 항목)
        if (!foundMatch) {
            singleMenuLinks.forEach(link => {
                const href = link.getAttribute('href');
                if (href === currentPath) {
                    // sidebar-item에 active 클래스 추가
                    const sidebarItem = link.closest('.sidebar-item');
                    if (sidebarItem) {
                        sidebarItem.classList.add('active');
                    }
                    foundMatch = true;
                }
            });
        }

        // 3. 정확한 일치가 없을 경우, sessionStorage에 저장된 값 확인
        if (!foundMatch) {
            const storedActiveLink = sessionStorage.getItem('activeLink');
            if (storedActiveLink) {
                // submenu 항목 확인
                submenuLinks.forEach(link => {
                    if (link.getAttribute('href') === storedActiveLink) {
                        // submenu-item에 active 클래스 추가
                        const submenuItem = link.closest('.submenu-item');
                        if (submenuItem) {
                            submenuItem.classList.add('active');
                        }

                        // 부모 sidebar-item에 active 클래스 추가
                        const sidebarItem = link.closest('.sidebar-item');
                        if (sidebarItem) {
                            sidebarItem.classList.add('active');
                        }

                        // submenu 표시
                        const submenu = sidebarItem.querySelector('.submenu');
                        if (submenu) {
                            submenu.style.display = 'block';
                        }

                        foundMatch = true;
                    }
                });

                // 일반 메뉴 항목 확인
                if (!foundMatch) {
                    singleMenuLinks.forEach(link => {
                        if (link.getAttribute('href') === storedActiveLink) {
                            const sidebarItem = link.closest('.sidebar-item');
                            if (sidebarItem) {
                                sidebarItem.classList.add('active');
                            }
                            foundMatch = true;
                        }
                    });
                }
            }
        }

        // 4. 여전히 일치하는 것이 없으면 대시보드를 기본값으로 활성화
        if (!foundMatch) {
            const dashboardItem = document.querySelector('.sidebar-item a[href="/dashboard"]');
            if (dashboardItem) {
                const sidebarItem = dashboardItem.closest('.sidebar-item');
                if (sidebarItem) {
                    sidebarItem.classList.add('active');
                }
            }
        }
    }

    // 페이지 로드 시 메뉴 활성화 실행
    activateMenuByUrl();

    // 사이드바 토글 기능 (has-sub 클래스가 있는 항목 클릭 시)
    const menuItemsWithSub = document.querySelectorAll('.sidebar-item.has-sub > .sidebar-link');
    menuItemsWithSub.forEach(item => {
        item.addEventListener('click', function(e) {
            e.preventDefault();

            // 모든 서브메뉴 닫기
            document.querySelectorAll('.sidebar-item.has-sub').forEach(otherItem => {
                if (otherItem !== this.parentElement) {
                    const otherSubmenu = otherItem.querySelector('.submenu');
                    if (otherSubmenu) {
                        otherSubmenu.style.display = 'none';
                    }
                }
            });

            const parent = this.parentElement;
            const submenu = parent.querySelector('.submenu');

            // 토글 submenu 표시/숨김
            if (submenu) {
                const isActive = submenu.style.display === 'block';

                if (isActive) {
                    submenu.style.display = 'none';
                } else {
                    submenu.style.display = 'block';
                }
            }
        });
    });
});