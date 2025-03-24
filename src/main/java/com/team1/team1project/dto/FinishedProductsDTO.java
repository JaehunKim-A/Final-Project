package com.team1.team1project.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FinishedProductsDTO {
    private int productId; // 제품 id
    private int codeId;  // 코드 id
    private String productDescription;  // 제품 정보
    private Date productionDate;    // 제품만들어진 날짜
    private String status;
    private LocalDateTime regDate;
    private LocalDateTime modDate;
}
