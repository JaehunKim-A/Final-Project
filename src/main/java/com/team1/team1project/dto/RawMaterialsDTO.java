package com.team1.team1project.dto;

import com.team1.team1project.domain.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RawMaterialsDTO extends BaseEntity {
    private Long materialId; // 원자재 아이디
    private String materialCode; // 원자재 코드
    private String materialName; // 원자재 이름
    private String unit; // 원자재 규격
    private String description; // 원자재 정보사항
    private String category;
    private String registeredBy;
    private String updatedBy;
}
