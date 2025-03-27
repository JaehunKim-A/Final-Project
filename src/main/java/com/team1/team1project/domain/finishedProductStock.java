package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class finishedProductStock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long finishedProductStockId;
    private Long productCode;
    private Long finishedProductStock;
}
