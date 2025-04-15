package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MachineRawMaterialReserveDTO {

    private int reserveId;

    @NotEmpty
    private String machineId;

    @NotEmpty
    private String materialCode;

    @NotEmpty
    private int stock;
}
