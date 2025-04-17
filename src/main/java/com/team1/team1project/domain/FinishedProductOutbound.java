package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "finished_product_outbounds")  // ✅ 테이블명 명시
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProductOutbound extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_id")
    private Long outboundId;  // ✅ 출고 ID (기본 키)

    @Column(name = "product_id", nullable = false)
    private Long productId;  // ✅ 제품 ID (외래 키 가능)

    @Column(nullable = false)
    private Long quantity;  // ✅ 출고 수량

    @Column(name = "outbound_date", nullable = false)
    private LocalDateTime outboundDate;  // ✅ 출고 날짜

    @Column(name = "outbound_code", length = 50, nullable = false)
    private String outboundCode;  // ✅ 출고 코드

    @Column(length = 50, nullable = false)
    private String status;  // ✅ 상태 (예: 완료, 보류 등)

    @PrePersist
    public void prePersist() {
        // `product_code` 자동 생성: UUID로 생성하여 `outbound_code`에 설정
        if (outboundCode == null) {
            this.outboundCode = "OUT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        }
    }
}
