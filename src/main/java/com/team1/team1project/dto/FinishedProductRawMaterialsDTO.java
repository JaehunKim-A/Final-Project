package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
// 콤보에 대한 관련 테이블 dto
public class FinishedProductRawMaterialsDTO {
    private Integer comboId;    // 콤보 id
    private Integer materialId; // 원자재 id
    private Integer materialQuantity;   // 콤보에 사용된 원자재 수량
}
