package com.team1.team1project.domain;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "combos")
public class RawMaterial extends BaseEntity {

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
    private int stock;  // 원자재 재고

    // FinishedProductCombos와의 관계 설정
    @OneToMany(mappedBy = "rawMaterial")
    private List<FinishedProductCombos> combos; // 해당 원자재를 사용하는 완제품 정보
}
