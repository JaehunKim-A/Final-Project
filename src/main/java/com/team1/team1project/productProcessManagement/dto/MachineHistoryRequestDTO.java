package com.team1.team1project.productProcessManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineHistoryRequestDTO {
    private int page;
    private int size;
    private String type;
    private String keyword;
    private String sorter;
    private boolean isAsc;
}
