package com.team1.team1project.dto;

import com.team1.team1project.domain.FinishedProductInbound;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProductInboundDTO {
    private Long inboundId;
    private String productCode;
    private Long quantity;
    private String inboundCode;
    private LocalDateTime inboundCompleteTime;
    private String status;
    private Long supplierId;

    // Entity -> DTO 변환
    public FinishedProductInboundDTO(FinishedProductInbound inbound) {
        this.inboundId = inbound.getInboundId();
        this.quantity = inbound.getQuantity();
        this.status = inbound.getStatus();
        this.inboundCompleteTime = inbound.getInboundCompleteTime();
        this.inboundCode = inbound.getInboundCode();
        this.supplierId = inbound.getSupplierId();
    }
}
