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
    private Long inboundId;

    private Long quantity;
    private String inboundCode;

    // ✅ 필드명 통일 (inboundCompleteTime → completeTime)
    private LocalDateTime completeTime;

    private String status;
    private Long supplierId;

    public void setQuantity(long l) {
        this.quantity = l;
    }
}
