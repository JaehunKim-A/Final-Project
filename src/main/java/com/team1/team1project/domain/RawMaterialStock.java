package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class RawMaterialStock extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stockId")
    private Long rawMaterialStockId;

    private String materialCode;
    @Column(name = "stock")
    private Long rawMaterialStock;
}
