package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RawMaterials extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long materialId;  // 원자재 쌓이는 번호
    private String materialCode; // 원자재 코드
    private String materialName; // 원자재 이름

    private String unit;  // 코드로 변경하여 코드 관리 EA06 등 포장 단위

    private String category;
    private String description;  // 원자재 설명
    private String registeredBy; // 등록자
    private String updatedBy;   // 수정자


    //수정할 사항 * 원자재 ID 랑 코드 ID 는 변경 불가하게 하고 설명, 단위만 변경 가능하게
    public void rawMaterialsChange(String materialCode,
                                   String materialName,
                                   String category,
                                   String unit,
                                   String description) {
        this.materialName = materialName;
        this.materialCode = materialCode;
        this.category = category;
        this.unit = unit;
        this.description = description;
    }
}
