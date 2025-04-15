package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.dto.*;

public interface RawMaterialOutboundService {

    // ✅ 1. 페이징 + 검색 목록
    PageResponseDTO<RawMaterialOutboundDTO> getPagedRawMaterialOutbounds(PageRequestDTO pageRequestDTO);

    // ✅ 2. 입고 상세조회
    RawMaterialOutboundDTO getRawMaterialOutbound(Long outboundId);

    // ✅ 3. 입고 등록
    void createRawMaterialOutbound(RawMaterialOutboundDTO dto);

    // ✅ 4. 입고 상태 수정 (전체 or 상태만)
    void modifyOutboundStatus(Long outboundId, RawMaterialOutboundDTO dto);

    // ✅ 5. 입고 삭제
    void deleteRawMaterialOutbound(Long outboundId);

    // ✅ 6. 히스토리 조회
    PageResponseDTO<RawMaterialOutboundDTO> getRawMaterialOutboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO);
}
