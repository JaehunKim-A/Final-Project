package com.team1.team1project.dto;

import com.team1.team1project.domain.BaseEntity;
import lombok.*;

import javax.persistence.Id;
import java.time.LocalDateTime;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RawMaterialsDTO extends BaseEntity {
    private Long materialId; // 원자재 아이디
    private String materialCode; // 원자재 코드
    private String materialName; // 원자재 이름
    private String unit; // 원자재 규격
    private String description; // 원자재 정보사항
    private String category;
    private String registeredBy;
    private String updatedBy;
    private LocalDateTime regDate;  // 등록일
    private LocalDateTime modDate;  // 수정일
}
