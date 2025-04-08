package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.repository.RawMaterialInboundRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RawMaterialInboundServiceImpl implements RawMaterialInboundService {

    private final RawMaterialInboundRepository rawMaterialInboundRepository;

    public RawMaterialInboundServiceImpl(RawMaterialInboundRepository rawMaterialInboundRepository) {
        this.rawMaterialInboundRepository = rawMaterialInboundRepository;
    }

    // ✅ 1. 원자재 입고 목록 조회
    @Override
    public List<RawMaterialInbound> getAllRawMaterialInbounds() {
        return rawMaterialInboundRepository.findAll();
    }

    // ✅ 2. 원자재 입고 등록
    @Override
    public void createRawMaterialInbound(RawMaterialInbound rawMaterialInbound) {
        rawMaterialInboundRepository.save(rawMaterialInbound);
    }

    // ✅ 3. 원자재 입고 삭제
    @Override
    public void deleteRawMaterialInbound(Long inboundId) {
        RawMaterialInbound inbound = rawMaterialInboundRepository.findById(inboundId)
                .orElseThrow(() -> new IllegalArgumentException("입고 데이터를 찾을 수 없습니다. ID: " + inboundId));
        rawMaterialInboundRepository.delete(inbound);
    }

    // ✅ 4. 원자재 입고 수정
    @Override
    public void updateInbound(Long inboundId, RawMaterialInbound updatedInbound) {
        RawMaterialInbound inbound = rawMaterialInboundRepository.findById(inboundId)
                .orElseThrow(() -> new IllegalArgumentException("입고 데이터를 찾을 수 없습니다. ID: " + inboundId));

        inbound.setMaterialCode(updatedInbound.getMaterialCode());
        inbound.setQuantity(updatedInbound.getQuantity());
        inbound.setInboundDate(updatedInbound.getInboundDate());
        inbound.setInboundCode(updatedInbound.getInboundCode());
        inbound.setSupplierId(updatedInbound.getSupplierId());
        inbound.setStatus(updatedInbound.getStatus());

        rawMaterialInboundRepository.save(inbound);
    }

    // ✅ 5. 원자재 입고 ID로 조회
    @Override
    public RawMaterialInbound getRawMaterialInboundById(Long inboundId) {
        return rawMaterialInboundRepository.findById(inboundId)
                .orElseThrow(() -> new RuntimeException("입고 데이터를 찾을 수 없습니다. ID: " + inboundId));
    }
}
