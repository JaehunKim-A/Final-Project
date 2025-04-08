package com.team1.team1project.service.finishedProduct;

import com.team1.team1project.dto.FinishedProductInboundDTO;
import com.team1.team1project.domain.FinishedProductInbound;
import com.team1.team1project.repository.FinishedProductInboundRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FinishedProductInboundServiceImpl implements FinishedProductInboundService {

    private final FinishedProductInboundRepository finishedProductInboundRepository;

    public FinishedProductInboundServiceImpl(FinishedProductInboundRepository finishedProductInboundRepository) {
        this.finishedProductInboundRepository = finishedProductInboundRepository;
    }

    @Override
    public void createFinishedProductInbound(FinishedProductInboundDTO finishedProductInboundDTO) {
        FinishedProductInbound inbound = new FinishedProductInbound();
        inbound.setQuantity(finishedProductInboundDTO.getQuantity());
        inbound.setStatus(finishedProductInboundDTO.getStatus());
        inbound.setInboundCode(finishedProductInboundDTO.getInboundCode());
        inbound.setInboundCompleteTime(finishedProductInboundDTO.getInboundCompleteTime());
        inbound.setSupplierId(finishedProductInboundDTO.getSupplierId());

        finishedProductInboundRepository.save(inbound); // DB에 저장
    }

    @Override
    public void modifyInboundStatus(Long inboundId, String status) {
        FinishedProductInbound inbound = finishedProductInboundRepository.findById(inboundId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid inbound ID"));

        inbound.setStatus(status); // 상태 수정
        finishedProductInboundRepository.save(inbound); // 상태 저장
    }

    @Override
    public void deleteFinishedProductInbound(Long inboundId) {
        FinishedProductInbound inbound = finishedProductInboundRepository.findById(inboundId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid inbound ID"));

        finishedProductInboundRepository.delete(inbound); // 입고 정보 삭제
    }

    @Override
    public List<FinishedProductInboundDTO> getAllFinishedProductInbounds() {
        // 입고 목록 조회
        return finishedProductInboundRepository.findAll()
                .stream()
                .map(FinishedProductInboundDTO::new)  // 새로운 생성자를 사용하여 변환
                .collect(Collectors.toList());
    }
}
