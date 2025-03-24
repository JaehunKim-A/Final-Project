package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterials extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;  // 원자재 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id", referencedColumnName = "code_id")
    private CodeManagement codeManagement;  // 코드 ID (외래키)

    @Column(name = "material_description")
    private String materialDescription;  // 원자재 설명

    @Column(name = "unit")
    private String unit;  // 단위 (예: kg, pcs)

    @Column(name = "stock", nullable = false)
    private Long stock;  // 원자재 재고

    // 수정할 사항 *코
    public void rawMaterialsChange(String materialDescription, String unit, Long stock) {
        this.materialDescription = materialDescription;
        this.unit = unit;
        this.stock = stock;
    }

}
