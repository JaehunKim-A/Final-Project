package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
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

    @Column(name = "reg_date", updatable = false)
    private LocalDateTime regDate;  // 생성 일시

    @Column(name = "mod_date")
    private LocalDateTime modDate;  // 수정 일시

    // 생성 및 수정 시간 자동 처리
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.regDate = now;
        this.modDate = now;
    }

    @PreUpdate
    public void preUpdate() {
        this.modDate = LocalDateTime.now();
    }
}
