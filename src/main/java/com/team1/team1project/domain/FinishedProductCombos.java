package com.team1.team1project.domain;

import lombok.*;
import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"finishedProduct", "rawMaterial"})
public class FinishedProductCombos extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comboId; // 콤보 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "finished_product_id", referencedColumnName = "product_id")
    private FinishedProduct finishedProduct; // 완제품 엔티티와의 관계 설정

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    private RawMaterial rawMaterial; // 원자재 엔티티와의 관계 설정

    private int quantity; // 원자재의 수량
}
