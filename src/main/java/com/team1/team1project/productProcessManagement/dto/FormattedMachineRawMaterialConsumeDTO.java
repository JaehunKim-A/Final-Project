package com.team1.team1project.productProcessManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FormattedMachineRawMaterialConsumeDTO {

    @NotEmpty
    private String machineId;

    @NotEmpty
    private String materialCode;

    @NotEmpty
    private int quantity;
}
