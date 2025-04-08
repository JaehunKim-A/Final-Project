package com.team1.team1project.service.rawMaterial;

import com.team1.team1project.dto.RawMaterialInboundDTO;

import java.util.List;

public interface RawMaterialInboundService {
    List<RawMaterialInboundDTO> getAllRawMaterialInbounds();
    void createRawMaterialInbound(RawMaterialInboundDTO dto);
    void updateInbound(Long inboundId, RawMaterialInboundDTO dto);
    void deleteRawMaterialInbound(Long inboundId);
}
