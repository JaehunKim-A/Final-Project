package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "finished_product_outbounds")  // ✅ 테이블명 명시
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FinishedProductOutbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "outbound_id")
    private Integer outboundId;  // ✅ 출고 ID (기본 키)

    @Column(name = "product_id", nullable = false)
    private Integer productId;  // ✅ 제품 ID (외래 키 가능)

    @Column(nullable = false)
    private Integer quantity;  // ✅ 출고 수량

    @Column(name = "outbound_date", nullable = false)
    private LocalDate outboundDate;  // ✅ 출고 날짜

    @Column(name = "outbound_code", length = 50, nullable = false)
    private String outboundCode;  // ✅ 출고 코드

    @Column(length = 50, nullable = false)
    private String status;  // ✅ 상태 (예: 완료, 보류 등)

    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;  // ✅ 등록 일시

    @Column(name = "mod_date")
    private LocalDateTime modDate;  // ✅ 수정 일시

    // ✅ 생성 및 수정 시간 자동 처리
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.regDate = now;
        this.modDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modDate = LocalDateTime.now();
    }
}
