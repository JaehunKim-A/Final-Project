package com.team1.team1project.dto;

import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProductInboundDTO {

    private Long inboundId;
    private String inboundCode;
    private Long quantity;

    // ✅ 필드명 통일
    private LocalDateTime completeTime;

    private String status;
    private Long supplierId;
}
