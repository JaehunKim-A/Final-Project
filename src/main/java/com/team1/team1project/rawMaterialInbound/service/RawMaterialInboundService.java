
package com.team1.team1project.rawMaterialInbound.service;

import com.team1.team1project.domain.RawMaterialInbound;
import java.util.List;

public interface RawMaterialInboundService {

    // ✅ 원자재 입고 목록 조회
    List<RawMaterialInbound> getAllRawMaterialInbounds();

    // ✅ 원자재 입고 등록
    void createRawMaterialInbound(RawMaterialInbound rawMaterialInbound);

    // ✅ 원자재 입고 삭제
    void deleteRawMaterialInbound(Long inboundId);

    // ✅ 원자재 입고 수정
    void updateInbound(Long inboundId, RawMaterialInbound rawMaterialInbound);

    // ✅ 원자재 입고 ID로 조회
    RawMaterialInbound getRawMaterialInboundById(Long inboundId);
}
