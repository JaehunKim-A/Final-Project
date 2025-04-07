package com.team1.team1project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class MachineGuiInfoDTO {
    private Integer guiId;

    @NotEmpty
    private String machineId;

    @JsonProperty("xCoordinate")
    private int xCoordinate;

    @JsonProperty("yCoordinate")
    private int yCoordinate;

    @NotEmpty
    private String machineType;

    private LocalDateTime productionDate;
    private LocalDateTime productionDateUpdate;
}
