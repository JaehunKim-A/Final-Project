package com.team1.team1project.service.rawMaterial;


import com.team1.team1project.dto.RawMaterialOutboundDTO;

import java.util.List;

public interface RawMaterialOutboundService {
    void registerOutbound(RawMaterialOutboundDTO dto);
    void deleteOutbound(Long outboundId);
    void updateStatus(Long outboundId, String status);
    List<RawMaterialOutboundDTO> getAllOutbounds();
    RawMaterialOutboundDTO getOutboundById(Long outboundId);
}
