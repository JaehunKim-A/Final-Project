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
// 콤보에 대한 관련 테이블 dto
public class FinishedProductRawMaterialsDTO {
    private Integer comboId;    // 콤보 id
    private Integer materialId; // 원자재 id
    private Integer materialQuantity;   // 콤보에 사용된 원자재 수량
    private LocalDateTime regDate;  // 등록일
    private LocalDateTime modDate;  // 수정일
}
