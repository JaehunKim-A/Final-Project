package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.dto.*;

public interface RawMaterialInboundService {

    // ✅ 1. 페이징 + 검색 목록
    PageResponseDTO<RawMaterialInboundDTO> getPagedRawMaterialInbounds(PageRequestDTO pageRequestDTO);

    // ✅ 2. 입고 상세조회
    RawMaterialInboundDTO getRawMaterialInbound(Long inboundId);

    // ✅ 3. 입고 등록
    void createRawMaterialInbound(RawMaterialInboundDTO dto);

    // ✅ 4. 입고 상태 수정 (전체 or 상태만)
    void modifyInboundStatus(Long inboundId, RawMaterialInboundDTO dto);

    // ✅ 5. 입고 삭제
    void deleteRawMaterialInbound(Long inboundId);

    // ✅ 6. 히스토리 조회
    PageResponseDTO<RawMaterialInboundDTO> getRawMaterialInboundHistoryForTable(String sorter, boolean isAsc, PageRequestDTO pageRequestDTO);
}
