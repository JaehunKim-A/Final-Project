package com.team1.team1project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineHistoryDTO {

    @JsonProperty("historyId")
    private int historyId;

    @NotEmpty
    @JsonProperty("machineId")
    private String machineId;

    @NotNull
    @Min(0)
    @JsonProperty("productionAmount")
    private int productionAmount;

    @NotNull
    @Min(0)
    @JsonProperty("defectiveAmount")
    private int defectiveAmount;

    @JsonProperty("productionDate")
    private LocalDateTime productionDate;
    @JsonProperty("productionDateUpdate")
    private LocalDateTime productionDateUpdate;
}
