package com.team1.team1project.productProcessManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductionDataDTO {
    public List<Integer> productionAmountData;
    public String machineId;
}
