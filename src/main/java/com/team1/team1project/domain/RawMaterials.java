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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;  // 원자재 ID

    private Integer codeId;

    @Column(name = "material_description")
    private String materialDescription;  // 원자재 설명

    @Column(name = "unit")
    private String unit;  // 단위 (예: kg, pcs)

    //수정할 사항 * 원자재 ID 랑 코드 ID 는 변경 불가하게 하고 설명, 단위만 변경 가능하게
    public void rawMaterialsChange(String materialDescription, String unit) {
        this.materialDescription = materialDescription;
        this.unit = unit;
    }
}
