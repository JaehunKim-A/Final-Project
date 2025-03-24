package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;
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

    @Column(unique = true)
    private Integer codeId; // 완제품 코드

    private String productDescription; // 기타사항
    private String status; // 제품 상태 예시) 생상 완료, 재고 있음 등등

    public void finishedProductsChange(Integer codeId, String productDescription, String status){
        this.codeId = codeId;
        this.productDescription = productDescription;
        this.status = status;
    }

}
