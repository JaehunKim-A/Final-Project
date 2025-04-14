package com.team1.team1project.dto;

import com.team1.team1project.domain.RawMaterialInbound;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialInboundDTO {

    private Long inboundId;
    private String inboundCode;
    private LocalDate inboundDate;  // LocalDate로 변경
    private String materialCode;
    private Long quantity;
    private String status;
    private Long supplierId;
    private Long materialId;

    // 엔티티를 DTO로 변환하는 메소드
    public static RawMaterialInboundDTO fromEntity(RawMaterialInbound rawMaterialInbound) {
        return new RawMaterialInboundDTO(
                rawMaterialInbound.getInboundId(),
                rawMaterialInbound.getInboundCode(),
                rawMaterialInbound.getInboundDate(),  // Date -> LocalDate로 변환 필요 없음
                rawMaterialInbound.getMaterialCode(),
                rawMaterialInbound.getQuantity(),
                rawMaterialInbound.getStatus(),
                rawMaterialInbound.getSupplierId(),
                rawMaterialInbound.getMaterialId()
        );
    }

    // DTO를 엔티티로 변환하는 메소드 (옵션)
    public static RawMaterialInbound toEntity(RawMaterialInboundDTO dto) {
        RawMaterialInbound inbound = new RawMaterialInbound();
        inbound.setInboundId(dto.getInboundId());
        inbound.setInboundCode(dto.getInboundCode());
        inbound.setInboundDate(dto.getInboundDate());  // LocalDate 그대로 설정
        inbound.setMaterialCode(dto.getMaterialCode());
        inbound.setQuantity(dto.getQuantity());
        inbound.setStatus(dto.getStatus());
        inbound.setSupplierId(dto.getSupplierId());
        inbound.setMaterialId(dto.getMaterialId());
        return inbound;
    }
}
