package com.team1.team1project.domain;


import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(value = {AbstractMethodError.class})
@Getter
public class BaseEntity {
    @CreatedDate
    @Column(name = "regdate", updatable = false)
    private LocalDateTime regDate;

    @CreatedDate
    @Column(name = "modDate")
    private LocalDateTime modDate;

    @PreUpdate
    public void preUpdate() {
        this.modDate = LocalDateTime.now();
    }

    // 생성 및 수정 시간 자동 처리
    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        this.regDate = now;
        this.modDate = now;
    }
}
