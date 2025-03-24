package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "raw_material_inbounds")  // ✅ 테이블명 명시
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RawMaterialInbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inbound_id")
    private Integer inboundId;  // ✅ 입고 ID (기본 키)

    @Column(name = "material_id", nullable = false)
    private Integer materialId;  // ✅ 원자재 ID (외래 키 가능)

    @Column(nullable = false)
    private Integer quantity;  // ✅ 입고 수량

    @Column(name = "inbound_date", nullable = false)
    private LocalDate inboundDate;  // ✅ 입고 날짜

    @Column(name = "inbound_code", length = 50, nullable = false)
    private String inboundCode;  // ✅ 입고 코드

    @Column(name = "supplier_id", nullable = false)
    private Integer supplierId;  // ✅ 공급업체 ID

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
