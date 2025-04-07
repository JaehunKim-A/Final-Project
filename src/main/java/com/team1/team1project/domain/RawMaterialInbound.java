package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "raw_material_inbounds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RawMaterialInbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inbound_id")
    private Long inboundId;  // 입고 ID (long 타입)

    @Column(name = "inbound_code", length = 50, nullable = false)
    private String inboundCode;  // 입고 코드 (string)

    @Column(name = "inbound_date", nullable = false)
    private LocalDate inboundDate;  // 입고 날짜 (Date)

    @Column(name = "material_code", length = 50, nullable = false)
    private String materialCode;  // 원자재 코드 (string)

    @Column(name = "quantity", nullable = false)
    private Long quantity;  // 입고 수량 (long)

    @Column(name = "status", length = 50, nullable = false)
    private String status;  // 상태 (string)

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;  // 공급업체 ID (long)

    @Column(name = "material_id", nullable = false)
    private Long materialId;  // 원자재 ID (long)

    // 생성 및 수정 시간 자동 처리
    @PrePersist
    public void prePersist() {
        // 최초 등록 시 필요한 작업 (ex. 등록 일시)
    }

    @PreUpdate
    public void preUpdate() {
        // 수정 시 필요한 작업
    }
}
