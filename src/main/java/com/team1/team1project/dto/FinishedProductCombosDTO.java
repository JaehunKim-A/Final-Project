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
public class FinishedProductCombosDTO {
    private int productId;  // 완제품 id
    private Long stock;     // 완제품 재고
    private LocalDateTime regDate;  // 등록일
    private LocalDateTime modDate;  // 수정일
}
