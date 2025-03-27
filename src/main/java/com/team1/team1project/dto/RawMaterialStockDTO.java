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
public class RawMaterialStockDTO {
    private Long rawMaterialStockId; // 원자재 id
    private Long materialCode;
    private Long rawMaterialStock; // 원자재 재고
}
