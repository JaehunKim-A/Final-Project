package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.dto.*;

public interface FinishedProductInboundService {

    // ✅ 1. 페이징 + 검색 목록
    PageResponseDTO<FinishedProductInboundDTO> getPagedFinishedProductInbounds(PageRequestDTO pageRequestDTO);

    // ✅ 2. 입고 상세조회
    FinishedProductInboundDTO getFinishedProductInbound(Long inboundId);

    // ✅ 3. 입고 등록
    void createFinishedProductInbound(FinishedProductInboundDTO dto);

    // ✅ 4. 입고 상태 수정 (전체 or 상태만)
    void modifyInboundStatus(Long inboundId, FinishedProductInboundDTO dto);

    // ✅ 5. 입고 삭제
    void deleteFinishedProductInbound(Long inboundId);

    // ✅ 6. 히스토리 조회
    PageResponseDTO<FinishedProductInboundDTO> getFinishedProductInboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO);
}
