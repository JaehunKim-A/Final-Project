package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeManagementDTO {
    private int comboId;    // 콤보 id
    private int codeId;     // 코드 Id
    private String comboDescription;    // 콤보 정보
    private Long comboQuantity; // 콤보 수량
    private LocalDateTime regDate;  // 등록일
    private LocalDateTime modDate;  // 수정일
}
