package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer product_id; // 제품 ID
    private String productName; // 제품 이름
    private String productDescription; // 제품 설명
    private String unit; // 단위 (예: pcs, kg 등)
    private Integer stock = 0; // 재고
}
