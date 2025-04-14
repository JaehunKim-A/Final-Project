package com.team1.team1project.dto;

import com.team1.team1project.domain.RawMaterialOutbound;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterialOutboundDTO {

    private Long outboundId;           // 출고 ID
    private String outboundCode;          // 출고 코드
    private LocalDateTime outboundDate;   // 출고 날짜
    private Long materialId;           // 원자재 ID
    private Long quantity;             // 출고 수량
    private String status;                // 상태
    private String warehouse;             // 창고 위치
    private LocalDateTime regDate;        // 등록일
    private LocalDateTime modDate;        // 수정일

    public RawMaterialOutbound toEntity() {
        return new RawMaterialOutbound(
                this.outboundId,
                this.outboundCode,
                this.outboundDate,
                this.materialId,
                this.quantity,
                this.status,
                this.warehouse,
                this.regDate,
                this.modDate
        );
    }

    public static RawMaterialOutboundDTO fromEntity(RawMaterialOutbound entity) {
        return new RawMaterialOutboundDTO(
                entity.getOutboundId(),
                entity.getOutboundCode(),
                entity.getOutboundDate(),
                entity.getMaterialId(),
                entity.getQuantity(),
                entity.getStatus(),
                entity.getWarehouse(),
                entity.getRegDate(),
                entity.getModDate()
        );
    }
}
