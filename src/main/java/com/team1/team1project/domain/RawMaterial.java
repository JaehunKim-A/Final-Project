package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "material_id")
    private Long materialId;  // 원자재 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "code_id", referencedColumnName = "code_id")
    private CodeManagement codeManagement;  // 코드 ID (외래키)

    @Column(name = "material_description")
    private String materialDescription;  // 원자재 설명

    @Column(name = "unit")
    private String unit;  // 단위 (예: kg, pcs)

    @Column(name = "stock", nullable = false)
    private int stock;  // 원자재 재고

    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;  // 생성 일시

    @Column(name = "mod_date")
    private LocalDateTime modDate;  // 수정 일시

    // 생성 및 수정 시간 자동 처리
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

