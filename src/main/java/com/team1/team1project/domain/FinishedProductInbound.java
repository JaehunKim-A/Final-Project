package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProductInbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer inboundId; // 입고 ID

    private Integer productId; // 완제품 ID
    private Integer quantity; // 입고 수량
    private String inboundCode; // 입출고 코드
    private Integer supplierId; // 공급업체 ID
    private String status; // 상태 (입고 완료 등)
    private Timestamp inboundCompleteTime; // 입고 완료 시간
}
