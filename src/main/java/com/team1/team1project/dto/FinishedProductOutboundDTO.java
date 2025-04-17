package com.team1.team1project.dto;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProductOutboundDTO {

    private Long outboundId;  // 출고 ID
    private Long productId;   // 제품 ID
    private Long quantity;    // 출고 수량

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")  // 날짜 시간 포맷
    private LocalDateTime outboundDate;  // 출고 날짜 (LocalDateTime으로 변경)

    private String outboundCode;  // 출고 코드
    private String status;    // 상태 (예: 완료, 보류 등)

}
