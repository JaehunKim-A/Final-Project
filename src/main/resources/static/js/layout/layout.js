const MY_API_KEY = "AIzaSyAsGRkWZkXXpBGhT4IjwLicHedgoEHMJ5A";
document.addEventListener('DOMContentLoaded', function() {
    // Get references to elements
    const chatButton = document.querySelector('.chat-bot');
    const chatModal = document.getElementById('chatModal');

    // Add click event to the chat button
    chatButton.addEventListener('click', function(e) {
        e.preventDefault();

        // Show the modal programmatically
        const modal = new bootstrap.Modal(chatModal);
        modal.show();

        // Ensure modal has the correct positioning
        fixModalPosition();
    });

    // Add event listener for when modal is shown
    chatModal.addEventListener('shown.bs.modal', function() {
        fixModalPosition();
    });

    // Function to fix modal position
    function fixModalPosition() {
        const modalDialog = chatModal.querySelector('.modal-dialog');

        // Apply fixed positioning
        modalDialog.style.position = 'fixed';
        modalDialog.style.bottom = '90px';
        modalDialog.style.right = '20px';
        modalDialog.style.transform = 'none';
        modalDialog.style.margin = '0';

        // Force modal to be visible
        chatModal.style.paddingRight = '0 !important';
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
const INITIAL_PROMPT = `당신은 Alfy라는 이름의 지능형 AI 어시스턴트입니다.
당신의 역할은 사용자에게 친절하고 도움이 되는 응답을 제공하는 것입니다.
Mazer Dashboard에 대한 질문이 있으면 이것이 Bootstrap 기반의 관리자 대시보드 템플릿이라고 설명해주세요.
짧고 간결하게 대답하되, 필요한 모든 정보를 포함시키세요.
전문적이지만 친근한 톤을 유지하세요.`;

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

// 추가 CSS 스타일
const style = document.createElement('style');
style.textContent = `
    .typing-indicator {
        color: #888;
        font-style: italic;
    }

    .send-button {
        cursor: pointer;
    }

    .chat-box-card .card-body {
        overflow-y: auto;
        max-height: 300px;
    }
`;
document.head.appendChild(style);