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
public class RawMaterialsDTO {

    private int materialId; // 원자재 아이디
    private int[] codeId; // 원자재 코드
    private String materialDescription; // 원자재 정보사항
    private String unit; // 원자재 규격
    private Long stock; // 원자재 재고

    private LocalDateTime regDate; // 등록일 
    private LocalDateTime modDate; // 수정일
}
