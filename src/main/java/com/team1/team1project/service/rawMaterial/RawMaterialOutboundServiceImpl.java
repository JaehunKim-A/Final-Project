package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.domain.RawMaterialOutbound;
import com.team1.team1project.dto.RawMaterialOutboundDTO;
import com.team1.team1project.repository.RawMaterialOutboundRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RawMaterialOutboundServiceImpl implements RawMaterialOutboundService {

    private final RawMaterialOutboundRepository repository;

    @Override
    public void registerOutbound(RawMaterialOutboundDTO dto) {
        RawMaterialOutbound entity = dto.toEntity();
        repository.save(entity);
    }

    @Override
    public void deleteOutbound(Long outboundId) {
        repository.deleteById(outboundId);
    }

    @Override
    public void updateStatus(Long outboundId, String status) {
        RawMaterialOutbound outbound = repository.findById(outboundId)
                .orElseThrow(() -> new IllegalArgumentException("출고 내역을 찾을 수 없습니다: " + outboundId));
        outbound.setStatus(status);
        repository.save(outbound);
    }

    @Override
    public List<RawMaterialOutboundDTO> getAllOutbounds() {
        return repository.findAll()
                .stream()
                .map(RawMaterialOutboundDTO::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public RawMaterialOutboundDTO getOutboundById(Long outboundId) {
        RawMaterialOutbound entity = repository.findById(outboundId)
                .orElseThrow(() -> new IllegalArgumentException("출고 내역을 찾을 수 없습니다: " + outboundId));
        return RawMaterialOutboundDTO.fromEntity(entity);
    }
}
