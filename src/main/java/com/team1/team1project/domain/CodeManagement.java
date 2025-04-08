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
    private Long codeId; // 코드 id
    private String codeValue;  // 예 RM1001
    private String codeName; // 코드 이름
    @Column(name = "description")
    private String description; // 코드 기타사항
    @Column(name = "type")
    private String codeType; // 코드 타입 예) 원자재, 완제품
    private String category; // 코드 구분
    private String registeredBy; //등록자
    private String updatedBy;   // 수정자

    public void codeChange(String codeValue,
                           String codeName,
                           String codeType,
                           String category,
                           String description)
    {
        this.codeValue = codeValue;
        this.codeName = codeName;
        this.codeType = codeType;
        this.category = category;
        this.description = description;
    }
}
