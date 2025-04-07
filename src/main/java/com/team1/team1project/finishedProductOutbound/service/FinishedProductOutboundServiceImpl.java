package com.team1.team1project.finishedProductOutbound.service;

import com.team1.team1project.domain.FinishedProductOutbound;
import com.team1.team1project.dto.FinishedProductInboundDTO;
import com.team1.team1project.domain.FinishedProductInbound;
import com.team1.team1project.dto.FinishedProductOutboundDTO;
import com.team1.team1project.finishedProductInbound.repository.FinishedProductInboundRepository;
import com.team1.team1project.finishedProductOutbound.repository.FinishedProductOutboundRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinishedProductOutboundServiceImpl implements FinishedProductOutboundService {

    private final FinishedProductOutboundRepository finishedProductOutboundRepository;

    public FinishedProductOutboundServiceImpl(FinishedProductOutboundRepository finishedProductOutboundRepository) {
        this.finishedProductOutboundRepository = finishedProductOutboundRepository;
    }

    @Override
    public void createFinishedProductOutbound(FinishedProductOutboundDTO finishedProductOutboundDTO) {
        FinishedProductOutbound outbound = new FinishedProductOutbound();
        outbound.setProductId(finishedProductOutboundDTO.getProductId());
        outbound.setQuantity(finishedProductOutboundDTO.getQuantity());
        outbound.setStatus(finishedProductOutboundDTO.getStatus());
        outbound.setOutboundCode(finishedProductOutboundDTO.getOutboundCode());
        outbound.setOutboundDate(finishedProductOutboundDTO.getOutboundDate());  // 출고 날짜

        finishedProductOutboundRepository.save(outbound); // DB에 저장
    }

    @Override
    public void modifyOutboundStatus(Long outboundId, String status) {
        FinishedProductOutbound outbound = finishedProductOutboundRepository.findById(outboundId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid outbound ID"));

        outbound.setStatus(status); // 상태 수정
        finishedProductOutboundRepository.save(outbound); // 상태 저장
    }

    @Override
    public void deleteFinishedProductOutbound(Long outboundId) {
        FinishedProductOutbound outbound = finishedProductOutboundRepository.findById(outboundId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid outbound ID"));

        finishedProductOutboundRepository.delete(outbound); // 입고 정보 삭제
    }

    @Override
    public List<FinishedProductOutboundDTO> getAllFinishedProductOutbounds() {
        // 입고 목록 조회
        return finishedProductOutboundRepository.findAll()
                .stream()
                .map(FinishedProductOutboundDTO::new)  // 새로운 생성자를 사용하여 변환
                .collect(Collectors.toList());
    }
}
