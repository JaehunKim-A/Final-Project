package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FinishedProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stockId")
    private Long finishedProductStockId;

    private String productCode;
    private String productId;
    @Column(name = "stock")
    private Long finishedProductStock;
}
