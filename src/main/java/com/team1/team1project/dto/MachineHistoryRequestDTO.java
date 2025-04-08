package com.team1.team1project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("isAsc")
    private boolean asc;
}
