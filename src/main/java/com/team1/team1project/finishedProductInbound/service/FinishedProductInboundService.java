package com.team1.team1project.finishedProductInbound.service;

import com.team1.team1project.dto.FinishedProductInboundDTO;

import java.util.List;

public interface FinishedProductInboundService {

    // 입고 등록
    void createFinishedProductInbound(FinishedProductInboundDTO finishedProductInboundDTO);

    // 입고 상태 수정
    void modifyInboundStatus(Long inboundId, String status);

    // 입고 삭제
    void deleteFinishedProductInbound(Long inboundId);

    // 모든 입고 목록 조회
    List<FinishedProductInboundDTO> getAllFinishedProductInbounds();
}
