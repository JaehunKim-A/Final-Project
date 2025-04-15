package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
    private String productName;

    private String unit;   // 포장유닛 ex) 소형, 중형, 대형, 진열용
    private String status; // 제품 상태 예시) 생상 중, 생산 중단 등등
    private String category; // 제품 카테고리 ex) 보급형
    private String description;
    private String updatedBy; // 수정자
    private String registeredBy; // 등록자

    public void finishedProductsChange(String productCode,
                                       String productName,
                                       String category,
                                       String unit,
                                       String status,
                                       String description){
        this.productCode = productCode;
        this.productName = productName;
        this.category = category;
        this.unit = unit;
        this.status = status;
        this.description = description;
    }
}
