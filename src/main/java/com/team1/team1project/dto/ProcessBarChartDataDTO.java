package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessBarChartDataDTO {
    private String productName;
    private int quantity;
    private int productionTarget;
    private String LineCode;
}
