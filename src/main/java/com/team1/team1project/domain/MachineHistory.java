package com.team1.team1project.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MachineHistory {
    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int historyId;
    @Column(name = "machine_id")
    private String machineId;
    @Column(name = "production_amount")
    private int productionAmount;
    @Column(name = "defective_amount")
    private int defectiveAmount;

    @CreatedDate
    @Column(name = "production_date")
    private LocalDateTime productionDate;

    @LastModifiedDate
    @Column(name = "production_date_update")
    private LocalDateTime productionDateUpdate;
}
