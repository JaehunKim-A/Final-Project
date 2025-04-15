package com.team1.team1project.dto;

import com.team1.team1project.domain.FinishedProductOutbound;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProductOutboundDTO {

    private Long outboundId;  // 출고 ID
    private Long productId;  // 제품 ID
    private Long quantity;  // 출고 수량
    private LocalDate outboundDate;  // 출고 날짜
    private String outboundCode;  // 출고 코드
    private String status;  // 상태 (예: 완료, 보류 등)

}
