package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.dto.*;

public interface FinishedProductOutboundService {

    // ✅ 1. 페이징 + 검색 목록
    PageResponseDTO<FinishedProductOutboundDTO> getPagedFinishedProductOutbounds(PageRequestDTO pageRequestDTO);

    // ✅ 2. 입고 상세조회
    FinishedProductOutboundDTO getFinishedProductOutbound(Long outboundId);

    // ✅ 3. 입고 등록
    void createFinishedProductOutbound(FinishedProductOutboundDTO dto);

    // ✅ 4. 입고 상태 수정 (전체 or 상태만)
    void modifyOutboundStatus(Long outboundId, FinishedProductOutboundDTO dto);

    // ✅ 5. 입고 삭제
    void deleteFinishedProductOutbound(Long outboundId);

    // ✅ 6. 히스토리 조회
    PageResponseDTO<FinishedProductOutboundDTO> getFinishedProductOutboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO);
}
