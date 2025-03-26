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
    private Integer productId;  // 완제품 id

    private String productCategory; // 제품 카테고리 ex) 보급형
    private String packagingUnit;   // 포장유닛 ex) 소형, 중형, 대형, 진열용
    @Column(unique = true)
    private String productCode; // 완제품 코드
    private String registeredBy; // 등록자
    private String status; // 제품 상태 예시) 생상 중, 생산 중단 등등
}
