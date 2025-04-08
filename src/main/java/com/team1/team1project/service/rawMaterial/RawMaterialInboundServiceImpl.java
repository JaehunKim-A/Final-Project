package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.domain.RawMaterialInbound;
import com.team1.team1project.dto.RawMaterialInboundDTO;
import com.team1.team1project.repository.RawMaterialInboundRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RawMaterialInboundServiceImpl implements RawMaterialInboundService {

    private final RawMaterialInboundRepository rawMaterialInboundRepository;

    public RawMaterialInboundServiceImpl(RawMaterialInboundRepository rawMaterialInboundRepository) {
        this.rawMaterialInboundRepository = rawMaterialInboundRepository;
    }

    @Override
    public List<RawMaterialInboundDTO> getAllRawMaterialInbounds() {
        return rawMaterialInboundRepository.findAll().stream()
                .map(RawMaterialInboundDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public void createRawMaterialInbound(RawMaterialInboundDTO dto) {
        RawMaterialInbound entity = RawMaterialInboundDTO.toEntity(dto);
        rawMaterialInboundRepository.save(entity);
    }

    @Override
    public void updateInbound(Long inboundId, RawMaterialInboundDTO dto) {
        RawMaterialInbound existing = rawMaterialInboundRepository.findById(inboundId)
                .orElseThrow(() -> new RuntimeException("입고 정보 없음"));

        RawMaterialInbound updated = RawMaterialInboundDTO.toEntity(dto);
        updated.setInboundId(inboundId);  // ID 유지
        rawMaterialInboundRepository.save(updated);
    }

    @Override
    public void deleteRawMaterialInbound(Long inboundId) {
        rawMaterialInboundRepository.deleteById(inboundId);
    }
}
