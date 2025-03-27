package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishedProductStockDTO {
    private Long finishedStockId;
    private Long productCode; // 완제품 코드
    private Long stock; // 완제품 재고 inbound 완료 된 것만
}
