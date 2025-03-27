package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishedProductsDTO {
    private Long productId; // 제품 id
    private String productCode;  // 코드 id
    private String productName;

    private String description;  // 제품 기타사항
    private String unit;
    private String status;
    private String category;
    private String registeredBy;
    private String updatedBy;

    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
