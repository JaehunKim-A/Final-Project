package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FinishedProducts extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;  // 완제품 id
    @Column(unique = true)
    private String productCode; // 완제품 코드

    private String description;
    private LocalDate productionDate;

    private String unit;   // 포장유닛 ex) 소형, 중형, 대형, 진열용
    private String status; // 제품 상태 예시) 생상 중, 생산 중단 등등
    private String category; // 제품 카테고리 ex) 보급형
    private String updatedBy;
    private String registeredBy; // 등록자

    public void finishedProductsChange(String productCode,
                                       String description,
                                       LocalDate productionDate,
                                       String unit,
                                       String category){
        this.productCode = productCode;
        this.description = description;
        this.productionDate = productionDate;
        this.unit = unit;
        this.category = category;
    }
}
