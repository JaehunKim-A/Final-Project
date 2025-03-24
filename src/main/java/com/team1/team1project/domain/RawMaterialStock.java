package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RawMaterialStock extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer materialId; // 원자재 id

    @Column(name = "stock")
    private Long rawMaterialStock; // 원자재 재고

    public void rawMaterialStockChange(Long rawMaterialStock){
        this.rawMaterialStock = rawMaterialStock;
    }
}
