package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RawMaterials extends BaseEntity {

    @Id
    private Long materialId;  // 원자재 쌓이는 번호
    private String materialCode; // 원자재 코드
    private String material; // 원자재 이름

    @Column(name = "unit")
    private String unit;  // 코드로 변경하여 코드 관리 EA06 등 포장 단위

    @Column(name = "material_description")
    private String materialDescription;  // 원자재 설명



    //수정할 사항 * 원자재 ID 랑 코드 ID 는 변경 불가하게 하고 설명, 단위만 변경 가능하게
    public void rawMaterialsChange(String materialDescription, String unit, String codeValue) {
        this.codeValue = codeValue;
        this.materialDescription = materialDescription;
        this.unit = unit;
    }
}
