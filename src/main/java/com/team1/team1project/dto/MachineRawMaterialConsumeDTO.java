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
public class MachineRawMaterialConsumeDTO {

    private int consumeId;

    @NotEmpty
    private int materialId;

    @NotEmpty
    private int quantity;
}
