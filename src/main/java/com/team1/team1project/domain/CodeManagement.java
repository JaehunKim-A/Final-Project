package com.team1.team1project.domain;

import lombok.*;

import javax.persistence.*;

@Entity // jpa 에서 관리하는 entity 지정
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Getter
@Builder
public class CodeManagement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "code_id")
    private Long codeId;  // 코드 ID

    @Column(name = "code_type", nullable = false)
    private String codeType;  // 코드 타입 (예: 원자재, 완제품 등)

    @Column(name = "code_value", nullable = false)
    private String codeValue;  // 코드 값 (예: RM001, FP001 등)

    @Column(name = "code_description")
    private String codeDescription;  // 코드 설명

    public void codeManagementChange(String codeDescription, String codeValue, String codeType){
        this.codeDescription = codeDescription;
        this.codeValue = codeValue;
        this.codeType = codeType;
    }
}
