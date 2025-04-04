package com.team1.team1project.productProcessManagement.dto;

import com.team1.team1project.dto.MachineHistoryDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineHistoryYearDTO {
    private List<MachineHistoryDTO> dtoList;

    private int year;
}
