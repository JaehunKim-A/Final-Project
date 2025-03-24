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
    private Long codeId;
    private String codeValue; // 예 RM1001
    private String codeName; // 코드 이름
    private String codeDescription; // 코드 기타사항
    private String category; // 코드 구분
    private String codeType; // 코드 타입 예) 원자재, 완제품
    private String registeredBy; //등록자
    private String updatedBy;   // 수정자
}
