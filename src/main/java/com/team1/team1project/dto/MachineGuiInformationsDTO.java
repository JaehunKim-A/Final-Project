package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineGuiInformationsDTO {
    @NotEmpty
    private int guiId;

    @NotEmpty
    private String machineId;

    @NotEmpty
    private int xCoordinate;

    @NotEmpty
    private int yCoordinate;

    @NotEmpty
    private String machineType;

    @NotEmpty
    private String materialCode;

    @NotEmpty
    private int stock;


}
