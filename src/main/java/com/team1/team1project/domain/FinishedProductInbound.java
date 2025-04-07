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
@Table(name = "finished_product_inbounds")
public class FinishedProductInbound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inboundId;  // 입고 ID

    // ✅ 완전히 제거: product나 productId 없이 사용
    // private FinishedProducts product;
    // private Long productId;

    private Long quantity;
    private String inboundCode;
    private LocalDateTime inboundCompleteTime;
    private String status;
    private Long supplierId;

    public void setQuantity(long l) {
        this.quantity = l;
    }
}
