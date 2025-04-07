package com.team1.team1project.finishedProductOutbound.service;

import com.team1.team1project.dto.FinishedProductInboundDTO;
import com.team1.team1project.dto.FinishedProductOutboundDTO;

import java.util.List;

public interface FinishedProductOutboundService {

    // 입고 등록
    void createFinishedProductOutbound(FinishedProductOutboundDTO finishedProductInboundDTO);

    // 입고 상태 수정
    void modifyOutboundStatus(Long outboundId, String status);

    // 입고 삭제
    void deleteFinishedProductOutbound(Long outboundId);

    // 모든 입고 목록 조회
    List<FinishedProductOutboundDTO> getAllFinishedProductOutbounds();
}
