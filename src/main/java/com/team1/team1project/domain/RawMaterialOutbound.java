package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "raw_material_outbounds")
@AllArgsConstructor
public class RawMaterialOutbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long outboundId;      // 출고 ID

    private String outboundCode;     // 출고 코드
    private LocalDateTime outboundDate;  // 출고 날짜
    private Long materialId;      // 원자재 ID
    private Long quantity;        // 출고 수량
    private String status;           // 상태
    private String warehouse;        // 창고 위치

    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;   // 등록 일시

    @Column(name = "mod_date")
    private LocalDateTime modDate;   // 수정 일시

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
