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
public class RawMaterialOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;  // 주문 ID

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "material_id", referencedColumnName = "material_id")
    private RawMaterial rawMaterial;  // 원자재 ID (다른 엔티티와 연결)

    @Column(name = "quantity", nullable = false)
    private int quantity;  // 주문 수량

    @Column(name = "order_date")
    private LocalDateTime orderDate;  // 주문 일자

    @Column(name = "status")
    private String status;  // 상태 (예: 처리 중, 완료)

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // 생성 일시

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;  // 수정 일시

    // 생성 및 수정 시간 자동 처리
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

