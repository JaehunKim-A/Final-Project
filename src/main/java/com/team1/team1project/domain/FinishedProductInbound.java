package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FinishedProductInbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inboundId;  // 입고 ID

    @ManyToOne(fetch = FetchType.LAZY) // ✅ 제품 테이블과 관계 설정
    @JoinColumn(name = "product_id", nullable = false)
    private FinishedProducts product;

    private Long quantity;
    private String inboundCode;
    private LocalDateTime inboundCompleteTime;
    private String status;
    private Long supplierId;

    public void setQuantity(long l) {
    }
}
