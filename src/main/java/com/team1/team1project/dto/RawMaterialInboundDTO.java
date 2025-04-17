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
    private LocalDate inboundDate;
    private Long quantity;
    private String status;
    private String materialCode;

    // 엔티티를 DTO로 변환하는 메소드
    public static RawMaterialInboundDTO fromEntity(RawMaterialInbound rawMaterialInbound) {
        return new RawMaterialInboundDTO(
                rawMaterialInbound.getInboundId(),
                rawMaterialInbound.getInboundCode(),
                rawMaterialInbound.getInboundDate(),
                rawMaterialInbound.getQuantity(),
                rawMaterialInbound.getStatus(),
                rawMaterialInbound.getMaterialCode() // 추가된 필드
        );
    }

    // DTO를 엔티티로 변환하는 메소드
    public static RawMaterialInbound toEntity(RawMaterialInboundDTO dto) {
        RawMaterialInbound inbound = new RawMaterialInbound();
        inbound.setInboundId(dto.getInboundId());
        inbound.setInboundCode(dto.getInboundCode());
        inbound.setInboundDate(dto.getInboundDate());
        inbound.setQuantity(dto.getQuantity());
        inbound.setStatus(dto.getStatus());
        inbound.setMaterialCode(dto.getMaterialCode()); // 추가된 필드
        return inbound;
    }
}
