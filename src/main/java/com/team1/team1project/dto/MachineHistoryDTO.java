package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineHistoryDTO {

    private int historyId;

    @NotEmpty
    private String machineId;

    @NotEmpty
    private int productionAmount;

    @NotEmpty
    private int defectiveAmount;

    private LocalDateTime productionDate;
    private LocalDateTime productionDateUpdate;
}
