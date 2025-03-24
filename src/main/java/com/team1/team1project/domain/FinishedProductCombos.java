package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class FinishedProductCombos extends BaseEntity{

    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Integer comboId; // 콤보id

    private Integer codeId; // 제품 코드에서 받아오는 codeId

    private String comboDescription; // 콤보 설명
    
    // 콤보 테이블은 제품에 대한 레시피

    // 설명만 변경 가능
    public void finishedProductCombosChange(Integer codeId,String comboDescription){
        this.comboDescription = comboDescription;
    }
}
