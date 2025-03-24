package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity // jpa 에서 관리하는 entity 지정
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Builder
public class FinishedProductStock extends BaseEntity {
    @Id
    private Integer codeId; // 완제품 코드 id 가져올것임
    private Long stock; // 완제품 재고
    // stock 은 트리거로 자동 수량 변경 되도록
}
